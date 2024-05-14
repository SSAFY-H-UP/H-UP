import styles from './TeamManagement.module.scss';
import ProjectManagement from './ProjectMangement';
import TeamManagement from './TeamManagement';
import Modal from 'react-modal';
import { useState } from 'react';

const SettingContainer = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [settingOption, setSettingOption] = useState('');

  return (
    <div>
      <button onClick={() => setIsOpen(!isOpen)}>일단클릭</button>
      <Modal isOpen={isOpen}>
        <h1>Settings</h1>
        <div>
          <button onClick={() => setSettingOption('team')}>
            Team Management
          </button>
          <button onClick={() => setSettingOption('project')}>
            Project Management
          </button>
        </div>
        <div>
          {settingOption === 'team' ? (
            <TeamManagement />
          ) : (
            <ProjectManagement />
          )}
        </div>
      </Modal>
    </div>
  );
};

export default SettingContainer;
