import { requestTeamMember } from '@api/services/setting';
import { useEffect, useState } from 'react';
import InviteMemberContainer from './InviteMemberContainer';
import { requestAllMember } from '@api/services/auth';
import { inviteTeamMember } from '@api/services/project';

const TeamManagementItem = ({ team }) => {
  const [teamMembers, setTeamMembers] = useState([]);
  const [members, setMembers] = useState([]);
  const [inviteMembers, setInviteMembers] = useState([])

  const getAllMemberInfo = async () => {
    const response = await requestAllMember();  
    setMembers(response.data.memberInfoList);
  }

  async function invite() { 
    const formData = {
        teamId: team.id,
        memberIdList: inviteMembers.map(member => member.id)
    };
    await inviteTeamMember(JSON.stringify(formData));
    alert('초대완료')
  }

  const getTeamMemberInfo = async () => {
    const response = await requestTeamMember(team.id);
    setTeamMembers(response.data.memberInfoList);
  };

  useEffect(() => {
    getTeamMemberInfo();
    getAllMemberInfo();
  }, []);

  return (
    <div>
      {team.name}
      {teamMembers && teamMembers.map(member => <div key={member.id}>{member.name}</div>)}
      <InviteMemberContainer members = {members} inviteMembers = {inviteMembers} setInviteMembers = {setInviteMembers} invite = {invite}/>
    </div>
  );
};

export default TeamManagementItem;
