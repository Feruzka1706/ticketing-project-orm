package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    @Override
    public List<RoleDTO> listAllRoles() {
      //bring all the roles from db
     //we need some helper to convert entity data to dto for controller layer - Mapper
     //There are different types of Mappers, each company might use different type of mappers
     //we gonna use Model Mapper in our project here
      //loop list of role entity record from db and convert each of them to dto by using roleMapper object methods

        return roleRepository.findAll().stream()
                .map(roleMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public RoleDTO findById(Long id) {

        return roleMapper.convertToDto(roleRepository.findById(id).get());
    }

}
