import { requestProject, requestTeamMember } from '@api/services/setting';
import { useEffect, useState } from 'react';
import ProjectMemberItem from './ProjectMemberItem';

const ProjectManagementItem = ({ team }) => {
  const [projects, setProjects] = useState([]);

  const getProject = async () => {
    const response = await requestProject(team.id);
    setProjects(response.data.projectInfoList);
  };

  useEffect(() => {
    getProject();
  }, []);

  return (
    <div>
      {team.name}
      {projects && projects.map(project => (
            <ProjectMemberItem key={project.id} team = {team} project = {project}/>
    ))}
    </div>
  );
};

export default ProjectManagementItem;
