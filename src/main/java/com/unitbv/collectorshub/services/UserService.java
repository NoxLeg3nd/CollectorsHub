package com.unitbv.collectorshub.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitbv.collectorshub.model.dto.AddUserDTO;
import com.unitbv.collectorshub.model.dto.UserDTO;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAll().stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build())
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<AddUserDTO> addUser(AddUserDTO addUserDTO) {
        User user = User.builder()
                .email(addUserDTO.getEmail())
                .username(addUserDTO.getUsername())
                .password(passwordEncoder.encode(addUserDTO.getPassword()))
                .build();
        if(userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(addUserDTO,HttpStatus.FOUND);
        }
        if(user.getEmail().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(addUserDTO,HttpStatus.CREATED);
    }
    public ResponseEntity<UserDTO> deleteUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getId());
        if(user.isEmpty()) {
            return new ResponseEntity<>(userDTO,HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user.get());
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
}
