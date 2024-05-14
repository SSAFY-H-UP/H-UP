import { requestMyTeam } from '@api/services/setting';
import { useEffect, useState } from 'react';
import ProjectManagementItem from './ProjectManagementItem';

const ProjectManagement = () => {
  const [teams, setTeams] = useState([]);

  const getTeamInfo = async() =>{
    const response = await requestMyTeam();
    setTeams(response.data.teamInfoList);
  }

  useEffect(()=>{
    getTeamInfo()
  },[]);


  return (
    <div>
      <h2>Project Management</h2>
      {teams && teams.map((team) =>
          <ProjectManagementItem key = {team.id} team = {team}/>
        )}
    </div>
  );
}

export default ProjectManagement;