import { inviteProjectMember, requestProjectMember, requestTeamMember } from '@api/services/setting';
import { useEffect, useState } from 'react';
import InviteMemberContainer from './InviteMemberContainer';

const ProjectMemberItem = ({ team, project }) => {
  const [members, setMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState([]);
  const [inviteMembers, setInviteMembers] = useState([]);
  
  const getMember = async () => {
    const response = await requestProjectMember(project.id);
    setMembers(response.data.memberInfoList);
  }

  const getTeamMember = async () => {
    const response = await requestTeamMember(team.id);
    setTeamMembers(response.data.memberInfoList);
  }

  async function invite() {
    const formData = {
        projectId: project.id,
        memberList: inviteMembers.map(member => member.id)
    };
    console.log(JSON.stringify(formData));
    await inviteProjectMember(JSON.stringify(formData));
    alert('초대완료')
  }


  useEffect(() => {
    getMember();
    getTeamMember();
  }, []);

  return (
    <div>
      {project.name}
      <div>
        {members && members.map(member => {
            return <div key={member.id}>{member.name}</div>
        })}
      </div>
      <InviteMemberContainer members={teamMembers} inviteMembers={inviteMembers} setInviteMembers={setInviteMembers} invite={invite}/>
    </div>
  );
};

export default ProjectMemberItem;