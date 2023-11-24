package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.User;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode (String projectCode);

    List<ProjectDTO> listAllProjects();

    void save(ProjectDTO projectDTO);

    void update(ProjectDTO projectDTO);
    void deleteProject(String projectCode);

    void complete(String projectCode);

    List<ProjectDTO> listAllProjectsDetails();

    List<ProjectDTO> readAllByAssignedManager(User assignedManager);
}
