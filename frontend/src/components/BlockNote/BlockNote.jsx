import { LoadIssueData } from '@api/services/issue';
import '@blocknote/core/fonts/inter.css';
import { BlockNoteView } from '@blocknote/mantine';
import '@blocknote/mantine/style.css';
import { useCreateBlockNote } from '@blocknote/react';
import { authState } from '@recoil/auth';
import { Stomp } from '@stomp/stompjs';
import { useCallback, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import SockJS from 'sockjs-client';
import * as Y from 'yjs';
import './IssueEditorPageBlockNote.css';

// Uploads a file to tmpfiles.org and returns the URL to the uploaded file.
async function uploadFile(file) {
  const body = new FormData();
  body.append("file", file);
 
  const ret = await fetch("https://tmpfiles.org/api/v1/upload", {
    method: "POST",
    body: body,
  });
  return (await ret.json()).data.url.replace(
    "tmpfiles.org/",
    "tmpfiles.org/dl/"
  );
}

function BlockNote() {
  const { id } = useParams();
  const [userInfo] = useRecoilState(authState);
  const stompClient = useRef(null);
  const ydoc = useRef(new Y.Doc()).current;

  const editor = useCreateBlockNote({
    collaboration: {
      fragment: ydoc.getXmlFragment('co-work'),
    },
    uploadFile
  });

  //   useEffect(() => {
  //     initializeAxios(token);
  //   }, [token]);

  // Fetch initial content and set up document
  useEffect(() => {
    async function fetchContent() {
      try {
        const response = await LoadIssueData(id);
        const contentData = JSON.parse(response.data.content);
        if (Array.isArray(contentData)) {
          Y.applyUpdate(ydoc, new Uint8Array(contentData));
          const xmlText = ydoc.getText('prosemirror');
        } else {
          console.error('Invalid initial content format');
        }
      } catch (error) {
        console.error('Error fetching initial content:', error);
      }
    }
    fetchContent();
  }, [id]);

  // Setup WebSocket connection and handlers
  useEffect(() => {
    const sock = new SockJS('https://h-up.site/api/ws');
    stompClient.current = Stomp.over(sock);
    stompClient.current.debug = () => {};
    stompClient.current.connect(
      {},
      () => {
        stompClient.current.subscribe(`/sub/documents/${id}`, message => {
          try {
            const updates = JSON.parse(JSON.parse(message.body).content);
            if (updates && Array.isArray(updates)) {
              const updateArray = new Uint8Array(updates);
              Y.applyUpdate(ydoc, updateArray, 'remote');
            } else {
              console.error('Invalid update format');
            }
          } catch (error) {
            console.error('Error applying updates:', error);
          }
        });

        ydoc.on('update', (update, origin) => {
          if (origin !== 'remote') {
            try {
              const updateArray = Y.encodeStateAsUpdate(ydoc);
              stompClient.current.send(
                `/pub/documents`,
                {},
                JSON.stringify({
                  documentsId: id,
                  memberId: userInfo.memberId,
                  content: JSON.stringify(Array.from(updateArray)),
                }),
              );
            } catch (error) {
              console.error('Error encoding state update:', error);
            }
          }
        });

        stompClient.current.send(
          `/pub/connection`,
          {},
          JSON.stringify({ documentsId: id, memberId: userInfo.memberId }),
        );
      },
      error => {
        console.error('Could not connect to STOMP server', error);
      },
    );

    return () => {
      if (stompClient.current && stompClient.current.connected) {
        stompClient.current.disconnect();
      }
    };
  }, [id, ydoc, userInfo.memberId]);

  const handleEditorChange = useCallback(
    change => {
      try {
        // Encode Yjs document state to Uint8Array
        const updateArray = Y.encodeStateAsUpdate(ydoc);

        // Optionally send the encoded state to server
        if (stompClient.current && stompClient.current.connected) {
          stompClient.current.send(
            `/pub/documents`,
            {},
            JSON.stringify({
              documentsId: id,
              memberId: userInfo.memberId,
              content: JSON.stringify(Array.from(updateArray)),
            }),
          );
        }
      } catch (error) {
        console.error('Error handling editor change:', error);
      }
    },
    [ydoc, userInfo.memberId, id],
  );

  return <BlockNoteView editor={editor} onChange={handleEditorChange} data-theming-css-variables-demo />;
}

export default BlockNote;