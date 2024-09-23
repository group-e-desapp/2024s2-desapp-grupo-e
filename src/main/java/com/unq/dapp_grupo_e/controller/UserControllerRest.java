package com.unq.dapp_grupo_e.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/user")
@RestController
public class UserControllerRest {

    private final UserService userService;


    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegisterDTO user) {
        var response = userService.createUser(user);
        
        return ResponseEntity.ok(response);
    }
    
    
}
