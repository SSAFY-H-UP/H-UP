// import useInput from '@hook/useInput';
// import useLogin from '@hook/useLogin';
// import { Styleshare } from '@styled-icons/simple-icons';
// import { useNavigate } from 'react-router-dom';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import TeamModal from "@component/Modal/TeamModal";
import ProjectModal from "@component/Modal/ProjectModal";

import styles from './FloatingButton.module.scss';

import { CreateTeam } from "@api/services/team";

const projectSubmit = async e => {

}

const issueCreate = () => {
    
}

const FloatingButton = () => {
    const [isTeamModalOpen, setIsTeamModalOpen] = useState(false);
    const openTeamModal = () => setIsTeamModalOpen(true);
    const [teamName, setTeamName] = useState('');
    // const closeTeamModal = () => setIsTeamModalOpen(false);
    const teamSubmit = async e => {
        setIsTeamModalOpen(false);
        const teamData = {
            teamName: teamName,
        };
    
        try {
            const response = await CreateTeam(teamData);
            console.log('Server Response: ', response.data);
    
            alert('팀 생성 성공');
            
            // 생성 성공 후 새로고침?
        } catch (error) {
            console.error(
                'CreateTeam error: ',
                error.response ? error.response.data : error,
            );
        }
    };

    const [isProjectModalOpen, setIsProjectModalOpen] = useState(false);
    const openProjectModal = () => setIsProjectModalOpen(true);
    const closeProjectModal = () => setIsProjectModalOpen(false);

    return (
        <div>
            <nav className={styles.nav}>
            <input type="checkbox" className={styles.nav__cb} id="menu-cb"/>
            {/* <Plus/> */}
            {/* <Plus type={PlusButtonType} className={PlusButtonClassName}/> */}
            <div className={styles.nav__content}>
                <ul className={styles.nav__items}>
                <li className={styles.nav__item}>
                    <span className={styles.nav__item_text} onClick={openTeamModal}>
                    팀 생성
                    </span>
                </li>
                <li className={styles.nav__item}>
                    <span className={styles.nav__item_text} onClick={openProjectModal}>
                    프로젝트 생성
                    </span>
                </li>
                <li className={styles.nav__item}>
                    <span className={styles.nav__item_text} onClick={issueCreate}>
                    이슈 생성
                    </span>
                </li>
                </ul>
            </div>
            <label className={styles.nav__btn} htmlFor="menu-cb"></label>
            </nav>

            <TeamModal isOpen={isTeamModalOpen} closeModal={ teamSubmit }>
                <div className={styles.team}>
                    <h2>팀 생성</h2>
                    <form onSubmit={teamSubmit}>
                        <input
                            className={styles.modal_team_input}
                            type='text'
                            placeholder='팀 이름을 입력하세요.'
                            onChange={e => setTeamName(e.target.value)}
                            required
                        />
                    </form>
                </div>
            </TeamModal>

            <ProjectModal isOpen={isProjectModalOpen} closeModal={closeProjectModal}>
                <div className={styles.project}>
                    <h2>프로젝트 생성</h2>
                    <form onSubmit={projectSubmit}>
                        <input
                            className={styles.modal_project_input}
                            type='text'
                            placeholder='프로젝트 이름을 입력하세요.'
                            onChange={e => setName(e.target.value)}
                            required
                        />
                    </form>
                </div>
            </ProjectModal>
        </div>
    );
};

export default FloatingButton;