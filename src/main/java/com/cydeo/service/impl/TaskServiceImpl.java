package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    TaskMapper taskMapper;
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
            convertedTask.setTaskStatus(foundTask.get().getTaskStatus());
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


}
