package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    @Override
    public TaskDTO findById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isPresent()){
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {

       return taskRepository.findAll().stream()
                .map(taskMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void save(TaskDTO taskDTO) {
      taskDTO.setTaskStatus(Status.OPEN);
      taskDTO.setAssignedDate(LocalDate.now());
      Task taskEntity = taskMapper.convertToEntity(taskDTO);
      taskRepository.save(taskEntity);

    }

    @Override
    public void update(TaskDTO taskDTO) {
        Optional<Task> foundTask = taskRepository.findById(taskDTO.getId());
        Task convertedTask = taskMapper.convertToEntity(taskDTO);

        if(foundTask.isPresent()){
            convertedTask.setId(foundTask.get().getId());
            convertedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? foundTask.get().getTaskStatus() : taskDTO.getTaskStatus());
            convertedTask.setAssignedDate(foundTask.get().getAssignedDate());

            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);

        if(foundTask.isPresent()){
         foundTask.get().setIsDeleted(true);
         taskRepository.save(foundTask.get());
        }

    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
      return   taskRepository.totalNonCompletedTasks(projectCode);

    }

    @Override
    public int totalCompletedTask(String projectCode) {
      return taskRepository.totalCompletedTasks(projectCode);

    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        List<TaskDTO> allTasks = listAllTasksByProject(projectDTO);
        allTasks.forEach(taskDto -> delete(taskDto.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        List<TaskDTO> allTasks = listAllTasksByProject(projectDTO);
        allTasks.forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

        //we are saving to SecurityContextHolder object from application run time
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User loggedInUser = userRepository.findByUserName(username);
        List<Task> allTasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status,loggedInUser);

        return allTasks.stream()
                .map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO taskDto) {
        Optional<Task> task = taskRepository.findById(taskDto.getId());

        if(task.isPresent()){
            task.get().setTaskStatus(taskDto.getTaskStatus());
            taskRepository.save(task.get());
        }

    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {

        //we are saving to SecurityContextHolder object from application run time
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User loggedInUser = userRepository.findByUserName(username);
        List<Task> allTasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status,loggedInUser);

        return allTasks.stream()
                .map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> readAllByAssignedEmployee(User assignedEmployee) {
        List<Task> listOfTasks = taskRepository.findAllByAssignedEmployee(assignedEmployee);

        return listOfTasks.stream()
                .map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    private List<TaskDTO> listAllTasksByProject(ProjectDTO projectDTO) {
       List<Task> allTasks= taskRepository.findAllByProject(projectMapper.convertToEntity(projectDTO));

       return allTasks.stream()
               .map(taskMapper::convertToDto).collect(Collectors.toList());
    }

}
