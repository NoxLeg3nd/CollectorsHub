package com.unitbv.collectorshub.controllers;

import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class UserController {
    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/addUser")
    public ResponseEntity<AddUserDTO> addUser(@RequestBody AddUserDTO addUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(addUserDTO));
    }
    @DeleteMapping("/removeUser")
    public ResponseEntity<Void> removeUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/loginUser")
    public ResponseEntity<GetUserDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return ResponseEntity.ok(userService.loginUser(loginUserDTO));
    }

    @PutMapping("/editUserDetails")
    public ResponseEntity<EditUserDetailsDTO> editUserDetails(@RequestParam Long id, @RequestBody EditUserDetailsDTO editUserDetailsDTO) {
        return ResponseEntity.ok(userService.editUserDetails(id, editUserDetailsDTO));
    }

    @PutMapping("/editUserPassword")
    public ResponseEntity<EditUserPasswordDTO> editUserPassword(@RequestParam Long id, @RequestBody EditUserPasswordDTO editUserPasswordDTO) {
        return ResponseEntity.ok(userService.editUserPassword(id, editUserPasswordDTO));
    }
}
