package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.ProjectDto;
import net.javaguides.ems.entity.Project;

public class ProjectMapper {

    public static ProjectDto mapToProjectDto(Project project) {

        if(project == null){
            return null;
        }

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setProjectName(project.getProjectName());

        return dto;
    }

    public static Project mapToProject(ProjectDto dto){

        if(dto == null){
            return null;
        }

        Project project = new Project();
        project.setId(dto.getId());
        project.setProjectName(dto.getProjectName());

        return project;
    }
}
