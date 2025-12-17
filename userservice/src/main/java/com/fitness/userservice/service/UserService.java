package com.fitness.userservice.service;

import javax.management.RuntimeErrorException;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequestDTO;
import com.fitness.userservice.dto.UserResponseDTO;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO register(RegisterRequestDTO request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exits");
        }

        User user = new User();
        user.setLastname(request.getLastname());
        user.setFirstname(request.getFirstname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstname(savedUser.getFirstname());
        response.setLastname(savedUser.getLastname());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;



    }

    public UserResponseDTO getUserProfile(String userId) {
        if(userRepository.existsByEmail(userId)){
            throw new RuntimeException("User does not exist");
        }
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;

    }
    
}
