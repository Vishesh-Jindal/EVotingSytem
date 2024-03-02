package com.example.eVoting.services;

import com.example.eVoting.dto.RegisterRequest;
import com.example.eVoting.dto.RoleRequest;
import com.example.eVoting.dto.RoleResponse;
import com.example.eVoting.dto.UserResponse;
import com.example.eVoting.entities.User;
import com.example.eVoting.exceptions.AlreadyExistsException;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserResponse registerUser(RegisterRequest registerRequest){
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if(optionalUser.isPresent()){
            throw new AlreadyExistsException("User is Already Present");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User();
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodedPassword);
        user.setDob(registerRequest.getDob());
        user.setAddress(registerRequest.getAddress());
        user.setGender(registerRequest.getGender());
        User createdUser = userRepository.save(user);
        return UserResponse.builder()
                .username(createdUser.getUsername())
                .name(createdUser.getName())
                .dob(createdUser.getDob())
                .gender(createdUser.getGender())
                .address(createdUser.getAddress())
                .build();
    }
    public UserResponse getUser(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new NotFoundException("User with id:"+username+" not found");
        }
        User user = optionalUser.get();
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .dob(user.getDob())
                .gender(user.getGender())
                .address(user.getAddress())
                .build();
    }
    public RoleResponse getUserRoles(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new NotFoundException("User:"+username+" not found");
        }
        User user = optionalUser.get();
        return RoleResponse.builder()
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
    public RoleResponse grantRoles(String username, RoleRequest roleRequest){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new NotFoundException("User:"+username+" not found");
        }
        User user = optionalUser.get();
        user.setRoles(roleRequest.getRoles());
        User updatedUser = userRepository.save(user);
        return RoleResponse.builder()
                .username(updatedUser.getUsername())
                .roles(updatedUser.getRoles())
                .build();
    }
    public RoleResponse revokeRoles(String username, RoleRequest roleRequest){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new NotFoundException("User:"+username+" not found");
        }
        User user = optionalUser.get();

        List<String> oldRoles = Arrays.asList(user.getRoles().split(","));
        List<String> toRevokeRoles = Arrays.asList(roleRequest.getRoles().split(","));
        String updatedRoles = "";
        for (String role:oldRoles) {
            if(!toRevokeRoles.contains(role)){
                updatedRoles+=role;
            }
        }
        user.setRoles(updatedRoles);
        User updatedUser = userRepository.save(user);
        return RoleResponse.builder()
                .username(updatedUser.getUsername())
                .roles(updatedUser.getRoles())
                .build();
    }
}
