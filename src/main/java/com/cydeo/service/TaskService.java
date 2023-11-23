package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long taskId);
    List<TaskDTO> listAllTasks();

    void save(TaskDTO taskDTO);

    void update(TaskDTO taskDTO);

    void delete(Long taskId);
}
