package com.unitbv.collectorshub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitbv.collectorshub.model.dto.UserDTO;
import com.unitbv.collectorshub.services.ProductService;
import com.unitbv.collectorshub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }
}
