package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> listAllUsers() {
        //controller calling me and requesting all the roles
        //need to go to DB and bring all roles from necessary entity table
        //Select * from users where is_deleted=false
        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream()
                .map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        //save method is doing update job as well
        //find current user with username
        User user = userRepository.findByUserName(userDTO.getUserName());

        //Map updated user dto to entity obj
        User convertedUser = userMapper.convertToEntity(userDTO);

        //set id to converted object
        convertedUser.setId(user.getId());

        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(userDTO.getUserName());
    }


    @Override
    public void deleteByUsername(String username) {

       userRepository.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {
        //we will not delete user record from db
        //change the flag as true and keep the record in db
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);

        //update the record in db
        userRepository.save(user);
    }


}
