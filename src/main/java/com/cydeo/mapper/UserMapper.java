package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    //if I want to use any method from ModelMapper class I need to do Dependency Injection

    //convertToEntity
    public User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }


    //convertToDto
    public UserDTO convertToDto(User userEntity){
        return modelMapper.map(userEntity,UserDTO.class);
    }

}
