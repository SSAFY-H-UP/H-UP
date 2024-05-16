import { useState } from 'react';

const InviteMemberContainer = ({ members, inviteMembers, setInviteMembers, invite}) => {
  const [memberName, setMemberName] = useState('');
  const [filteredMembers, setFileteredMember] = useState([]);

  const addInviteMember = member => {
    if(!inviteMembers.includes(member)){
      setInviteMembers([...inviteMembers,member]);
      handleMemberName('')
    }
  }

  const removeInviteMember = removeMember =>{
    setInviteMembers(inviteMembers.filter(member => member.id !== removeMember.id));
  }

  const handleMemberName = (name) =>{
    setMemberName(name);
    if (name.length > 0) {
      setFileteredMember(members.filter(member =>
        member.name.toLowerCase().includes(name) ||
        member.userId.toLowerCase().includes(name)
      ));
    }else{
      setFileteredMember([]);
    }
  }

  const handleMemberKeyDown = (e) => {
    if (e.key === 'Enter' && filteredMembers.length === 1) {
      addInviteMember(filteredMembers[0]);
    }
  }

  return (
    <div>
        <div>
            초대하기
        </div>
        {inviteMembers && inviteMembers.map((member) => (
            <div key = {member.id} onClick={() => removeInviteMember(member)}>{member.name} </div>
        ))}
        <input value={memberName} onChange={(e) => handleMemberName(e.target.value)} onKeyDown={handleMemberKeyDown}/>
        <button onClick={()=>invite()}>초대</button>
        {filteredMembers && filteredMembers.map(member => (
        <div key={member.id} onClick={()=>addInviteMember(member)}>
            {member.name}({member.userId})
        </div>
        ))}
    </div>
  );
};

export default InviteMemberContainer;