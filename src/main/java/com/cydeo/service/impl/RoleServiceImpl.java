package com.cydeo.service.impl;
import com.cydeo.dto.RoleDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository,
                           MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
      //bring all the roles from db
     //we need some helper to convert entity data to dto for controller layer - Mapper
     //There are different types of Mappers, each company might use different type of mappers
     //we gonna use Model Mapper in our project here
      //loop list of role entity record from db and convert each of them to dto by using roleMapper object methods

       // return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());

        return roleRepository.findAll().stream()
                .map(role -> mapperUtil.convert(role, new RoleDTO())).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
       // return roleMapper.convertToDto(roleRepository.findById(id).get());
        return mapperUtil.convert(roleRepository.findById(id).get(), new RoleDTO());
    }

}
