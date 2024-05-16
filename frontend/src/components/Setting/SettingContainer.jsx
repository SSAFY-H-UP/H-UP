import styles from './SettingContainer.module.scss';
import Modal from 'react-modal';
import { useState ,useEffect} from 'react';
import TeamManagementItem from './TeamManagementItem';
import ProjectManagementItem from './ProjectManagementItem';
import { requestMyTeam } from '@api/services/setting';

const SettingContainer = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [settingOption, setSettingOption] = useState('');
  const [teams, setTeams] = useState([]);

  const getTeamInfo = async () => {
    const response = await requestMyTeam();
    setTeams(response.data.teamInfoList);
  };

  useEffect(() => {
    getTeamInfo();
  }, []);

  return (
    <div>
      <button onClick={() => setIsOpen(!isOpen)}>일단클릭</button>
      <Modal isOpen={isOpen}>
        {/* header */}
        <div className={styles.header}>
          <h3>Settings</h3>
          <div className={styles.menu}>
            <div 
              className={`${styles.menu_item} ${settingOption === 'team' ? styles.active : ''}`} 
              onClick={() => setSettingOption('team')}>
                Team Management
            </div>
            <div 
              className={`${styles.menu_item} ${settingOption === 'project' ? styles.active : ''}`}
              onClick={() => setSettingOption('project')}>
                Project Management
            </div>
          </div>
        </div>
        {/* body */}
        <div>
          {settingOption === 'team' ? (
            <div>  
              {/* TEAM MANAGEMENT */}
              {teams &&
                teams.map(team => (
                  <TeamManagementItem key={team.id} team={team} />
                ))}
            </div>) : (
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
    </div>
  );
};

export default SettingContainer;
