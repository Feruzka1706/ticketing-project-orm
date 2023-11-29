package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

     private final ProjectRepository projectRepository;
     private final ProjectMapper projectMapper;
     private final UserService userService;
     private final UserMapper userMapper;

     private final MapperUtil mapperUtil;
     private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectMapper projectMapper,
                              UserService userService,
                              UserMapper userMapper,
                              MapperUtil mapperUtil,
                              TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.mapperUtil = mapperUtil;
        this.taskService = taskService;
    }

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

        project.setProjectCode(project.getProjectCode() + "-" + project.getId());

        //update the record in db
        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDto(project));
    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);

        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDto(project));
    }


    @Override
    public List<ProjectDTO> listAllProjectsDetails() {

        //we are saving to SecurityContextHolder object from application run time
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDTO currentUserDto = userService.findByUserName(username);
        User user = userMapper.convertToEntity(currentUserDto);

        //Let's say we have manager with email this mike@gmail.com
        //We want to return all list of projects from DB which is assigned to his name by searching the email address
       List<Project> listOfProjectsForAssignedManager= projectRepository.findAllByAssignedManager(user);

        return listOfProjectsForAssignedManager.stream()
                .map(project -> {
                    ProjectDTO obj = projectMapper.convertToDto(project);
                    obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
                    obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
                    return obj;
                }).collect(Collectors.toList());
    }


    @Override
    public List<ProjectDTO> readAllByAssignedManager(User assignedManager) {
        List<Project> listOfProjects = projectRepository.findAllByAssignedManager(assignedManager);

        return listOfProjects.stream()
                .map(projectMapper::convertToDto).collect(Collectors.toList());
    }
}
