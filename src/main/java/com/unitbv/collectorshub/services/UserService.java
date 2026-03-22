package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<GetUserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> GetUserDTO.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .toList();
    }

    public AddUserDTO addUser(AddUserDTO addUserDTO) {

        if (addUserDTO.getEmail().isBlank() ||
                addUserDTO.getUsername().isBlank() ||
                addUserDTO.getPassword().isBlank()) {
            throw new ApiException("Username, Email or Password cannot be empty", 400);
        }

        if (userRepository.findByUsername(addUserDTO.getUsername()).isPresent() ||
                userRepository.findByEmail(addUserDTO.getEmail()).isPresent()) {
            throw new ApiException("Username or Email already exists", 409);
        }

        User user = User.builder()
                .email(addUserDTO.getEmail())
                .username(addUserDTO.getUsername())
                .password(passwordEncoder.encode(addUserDTO.getPassword()))
                .build();

        userRepository.save(user);
        return addUserDTO;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", 404));

        userRepository.delete(user);
    }

    public GetUserDTO loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByUsername(loginUserDTO.getUsername())
                .orElseThrow(() -> new ApiException("User not found", 404));
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new ApiException("Wrong password", 401);
        }
        return GetUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public EditUserDetailsDTO editUserDetails(Long id, EditUserDetailsDTO editUserDetailsDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApiException("User not found", 404));
        if(editUserDetailsDTO.getNewEmail().isBlank() || editUserDetailsDTO.getNewUsername().isBlank()) {
            throw new ApiException("Email or Username cannot be empty", 400);
        }
        user.setEmail(editUserDetailsDTO.getNewEmail());
        user.setUsername(editUserDetailsDTO.getNewUsername());
        userRepository.save(user);
        return editUserDetailsDTO;
    }

    public EditUserPasswordDTO editUserPassword(Long id, EditUserPasswordDTO editUserPasswordDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", 404));

        if (editUserPasswordDTO.getCurrentPassword().isBlank() || editUserPasswordDTO.getNewPassword().isBlank()) {
            throw new ApiException("Password cannot be empty", 400);
        }

        if (!passwordEncoder.matches(editUserPasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new ApiException("Current password is incorrect", 401);
        }

        user.setPassword(passwordEncoder.encode(editUserPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return editUserPasswordDTO;
    }
}