package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    //if I want to use any method from ModelMapper class I need to do Dependency Injection

    //convertToEntity
    public Role convertToEntity(RoleDTO roleDTO){
        return modelMapper.map(roleDTO,Role.class);
    }


    //convertToDto
    public RoleDTO convertToDto(Role roleEntity){
        return modelMapper.map(roleEntity,RoleDTO.class);
    }


}
