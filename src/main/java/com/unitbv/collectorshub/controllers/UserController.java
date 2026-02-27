package com.unitbv.collectorshub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitbv.collectorshub.model.dto.AddUserDTO;
import com.unitbv.collectorshub.model.dto.UserDTO;
import com.unitbv.collectorshub.services.ProductService;
import com.unitbv.collectorshub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class UserController {
    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/addUser")
    public ResponseEntity<AddUserDTO> addUser(@RequestBody AddUserDTO addUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(addUserDTO));
    }
    @DeleteMapping("/removeUser")
    public ResponseEntity<Void>  removeUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
