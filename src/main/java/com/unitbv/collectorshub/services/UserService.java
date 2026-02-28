package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.AddUserDTO;
import com.unitbv.collectorshub.model.dto.GetUserDTO;
import com.unitbv.collectorshub.model.dto.LoginUserDTO;
import com.unitbv.collectorshub.model.dto.UserDTO;
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

    public String loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByUsername(loginUserDTO.getUsername())
                .orElseThrow(() -> new ApiException("User not found", 404));
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new ApiException("Wrong password", 401);
        }
        return "Login successful for user with credentials:\n" +
                " id: " + user.getId() +
                "\nusername: " + user.getUsername() +
                "\nemail: " + user.getEmail();
    }
}