import { requestTeamList } from '@api/services/team';
import { useEffect, useState } from 'react';
import Modal from 'react-modal';
import ProjectManagementItem from './ProjectManagementItem';
import styles from './SettingContainer.module.scss';
import TeamManagementItem from './TeamManagementItem';
import { useRecoilState } from 'recoil';
import { authState } from '@recoil/auth';

// 모달 스타일 정의
const modalStyles = {
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: '20',
  },
  content: {
    position: 'relative',
    background: 'white',
    width: '80%',
    maxWidth: '600px',
    margin: 'auto',
    borderRadius: '8px',
    padding: '20px',
    boxShadow: '0 5px 15px rgba(0, 0, 0, 0.3)',
  },
};

const SettingContainer = ({ isOpen, closeSetting }) => {
  // const [isOpen, setIsOpen] = useState(false);
  const [settingOption, setSettingOption] = useState('team');
  const [teams, setTeams] = useState([]);
  const [userInfo] = useRecoilState(authState);

  useEffect(() => {
    const getTeamInfo = async () => {
      const response = await requestTeamList();
      setTeams(response.data.teamInfoList);
    };

    if (userInfo.isLogin) getTeamInfo();
  }, []);

  return (
    <Modal isOpen={isOpen} onRequestClose={closeSetting} style={modalStyles}>
      {/* header */}
      <div className={styles.header}>
        <h3>Settings</h3>
        <button className={styles.closeButton} onClick={closeSetting}>
          &times;
        </button>
        <div className={styles.menu}>
          <div
            className={`${styles.menu_item} ${settingOption === 'team' ? styles.active : ''}`}
            onClick={() => setSettingOption('team')}
          >
            Team Management
          </div>
          <div
            className={`${styles.menu_item} ${settingOption === 'project' ? styles.active : ''}`}
            onClick={() => setSettingOption('project')}
          >
            Project Management
          </div>
        </div>
      </div>
      {/* body */}
      <div className={styles.body}>
        {settingOption === 'team' ? (
          <div>
            {/* TEAM MANAGEMENT */}
            {teams &&
              teams.map(team => (
                <TeamManagementItem key={team.id} team={team} />
              ))}
          </div>
        ) : (
          <div>
            {/* TEAM MANAGEMENT */}
            {teams &&
              teams.map(team => (
                <ProjectManagementItem key={team.id} team={team} />
              ))}
          </div>
        )}
      </div>
    </Modal>
  );
};

export default SettingContainer;
