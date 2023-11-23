package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    public Task convertToEntity(TaskDTO taskDTO){
        return modelMapper.map(taskDTO,Task.class);
    }

    public TaskDTO convertToDto(Task taskEntity){
        return modelMapper.map(taskEntity,TaskDTO.class);
    }


}
