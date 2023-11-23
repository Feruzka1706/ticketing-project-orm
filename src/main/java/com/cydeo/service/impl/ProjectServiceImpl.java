package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

     private final ProjectRepository projectRepository;
     private final ProjectMapper projectMapper;
     private final UserService userService;
     private final UserMapper userMapper;
     private final TaskService taskService;
    @Override
    public ProjectDTO getByProjectCode(String projectCode) {

        return projectMapper
                .convertToDto(projectRepository.findByProjectCode(projectCode));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll().stream()
                .map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);

        projectRepository.save(projectMapper.convertToEntity(projectDTO));
    }

    @Override
    public void update(ProjectDTO projectDTO) {
        //save method is doing update job as well
        //find current project with project code
        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());

        //Map updated user dto to entity obj
        Project convertedProject = projectMapper.convertToEntity(projectDTO);
        //set id to converted object
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());

        //save updated user
        projectRepository.save(convertedProject);

    }

    @Override
    public void deleteProject(String projectCode) {

        //we will not delete project record from db
        //change the flag as true and keep the record in db
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setIsDeleted(true);

        //update the record in db
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }


    @Override
    public List<ProjectDTO> listAllProjectsDetails() {

        UserDTO currentUserDto = userService.findByUserName("harold@manager.com");
        User user = userMapper.convertToEntity(currentUserDto);

        //Let's say we have manager with email this harold@manager.com
       List<Project> listProjects= projectRepository.findAllByAssignedManager(user);

        return listProjects.stream()
                .map(project -> {
                    ProjectDTO obj = projectMapper.convertToDto(project);
                    obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
                    obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
                    return obj;
                }).collect(Collectors.toList());
    }


}
