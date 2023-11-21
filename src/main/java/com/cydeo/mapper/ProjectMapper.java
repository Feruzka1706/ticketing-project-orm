package com.cydeo.mapper;


import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectMapper {

    private final ModelMapper modelMapper;

    //convertToEntity
    public Project convertToEntity(ProjectDTO projectDTO){

        return modelMapper.map(projectDTO,Project.class);
    }

    //convertToDto
    public ProjectDTO convertToDto(Project projectEntity){

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }

}
