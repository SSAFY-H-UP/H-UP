package com.a702.hup.domain.document.redis;

import com.a702.hup.application.data.request.SaveDocumentsRequest;
import com.a702.hup.application.data.response.DocumentsMembersResponse;
import com.a702.hup.application.data.response.DocumentsResponse;
import com.a702.hup.domain.document.DocumentException;
import com.a702.hup.application.data.dto.DocumentsMemberInfo;
import com.a702.hup.domain.document.mongodb.DocumentsMongoService;
import com.a702.hup.domain.document.redis.entity.ActiveDocumentsMembersRedis;
import com.a702.hup.domain.document.redis.entity.DocumentsRedis;
import com.a702.hup.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentsRedisService {
    private final ActiveDocumentsMembersRedisRepository activeDocumentsMembersRedisRepository;
    private final DocumentsRedisRepository documentsRedisRepository;
    private final DocumentsMongoService documentsMongoService;

    private boolean isRunning = false;
    private long lastStartTime = System.currentTimeMillis();

    /**
     * @author 손현조
     * @date 2024-04-29
     * @description 5초마다 문서 상태를 Redis 저장
     **/
    public DocumentsResponse saveDocument(SaveDocumentsRequest request) {
        long now = System.currentTimeMillis();
        if (isRunning || now - lastStartTime < 5000) {
            return createResponse(request);
        }

        isRunning = true;
        lastStartTime = now;

        try {
            processDocument(request);
        } finally {
            isRunning = false;
        }

        return createResponse(request);
    }

    /**
     * @author 손현조
     * @date 2024-04-29
     * @description 문서 업데이트
     **/
    private void processDocument(SaveDocumentsRequest request) {
        DocumentsRedis documentsRedis = findOrCreateDocumentsRedis(
                Integer.toString(request.getDocumentId()), Integer.toString(request.getSenderId()));
        documentsRedis.updateContent(request.getContent());

        documentsRedisRepository.save(documentsRedis);
    }

    /**
     * @author 손현조
     * @date 2024-04-29
     * @description 문서가 없다면, 생성
     **/
    private DocumentsRedis findOrCreateDocumentsRedis(String documentId, String senderId) {
        return documentsRedisRepository.findById(documentId)
                .orElseGet(() -> new DocumentsRedis(documentId, senderId));
    }

    private DocumentsResponse createResponse(SaveDocumentsRequest request) {
        return DocumentsResponse.from(request);
    }

    /**
     * @author 손현조
     * @date 2024-04-28
     * @description 문서를 사용중인 멤버 추가, 현재 사용중인 멤버들 정보 (Id, 이름, 이미지 url) 반환
     **/
    public DocumentsMembersResponse saveMember(String documentId, DocumentsMemberInfo memberDto){
        ActiveDocumentsMembersRedis activeDocumentsMembersRedis = activeDocumentsMembersRedisRepository.findById(documentId)
                .orElseGet(() -> new ActiveDocumentsMembersRedis(documentId));
        activeDocumentsMembersRedis.addMember(memberDto);
        return DocumentsMembersResponse.from(activeDocumentsMembersRedisRepository.save(activeDocumentsMembersRedis));
    }

    /**
     * @author 손현조
     * @date 2024-04-28
     * @description
     *      - 문서를 사용중인 멤버 제거, 현재 사용중인 멤버들 정보 (Id, 이름, 이미지 url) 반환
     *      - 문서를 사용중인 마지막 멤버가 연결을 종료하면, 문서 내용 영구 저장
     **/
    public DocumentsMembersResponse removeMember(String documentId, DocumentsMemberInfo memberDto) {
        ActiveDocumentsMembersRedis activeDocumentsMembersRedis = activeDocumentsMembersRedisRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(ErrorCode.API_ERROR_ACTIVE_DOCUMENT_MEMBER_NOT_FOUND));

        activeDocumentsMembersRedis.removeMember(memberDto);
        if (activeDocumentsMembersRedis.isDocumentMemberEmpty()) {
            documentsMongoService.save(documentId, getLatestContent(documentId));
        }
        return DocumentsMembersResponse.from(activeDocumentsMembersRedisRepository.save(activeDocumentsMembersRedis));
    }

    /**
     * @author 손현조
     * @date 2024-04-30
     * @description 최근 문서 내용 Redis 에서 가져오기
     **/
    private String getLatestContent(String documentId) {
        return findDocumentRedisById(documentId).getContent();
    }

    /**
     * @author 손현조
     * @date 2024-04-30
     * @description Redis 에서 문서 조회
     **/
    public DocumentsRedis findDocumentRedisById(String documentId) {
        return documentsRedisRepository.findById(documentId).orElseThrow(
                () -> new DocumentException(ErrorCode.API_ERROR_DOCUMENT_NOT_FOUND)
        );
    }


}
