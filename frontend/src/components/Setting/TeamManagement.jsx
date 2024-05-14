import { requestMyTeam } from '@api/services/setting';
import { useEffect, useState, useRef } from 'react';
import TeamManagementItem from './TeamManagementItem';

const TeamManagement = () => {
  const [teams, setTeams] = useState([]);

  const getTeamInfo = async () => {
    const response = await requestMyTeam();
    setTeams(response.data.teamInfoList);
  };

  useEffect(() => {
    getTeamInfo();
  }, []);

  return (
    <div className='team-management'>
      <h2>Team Management</h2>
        {teams &&
          teams.map(team => (
            <TeamManagementItem key={team.id} team={team} />
          ))}
    </div>
  );
};

export default TeamManagement;
