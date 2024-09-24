package com.unq.dapp_grupo_e.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/user")
@RestController
public class UserControllerRest {

    private final UserService userService;


    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses({
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    @Operation(summary = "Register of a new user")
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegisterDTO user) {
        var response = userService.createUser(user);
        
        return ResponseEntity.ok(response);
    }
    
    
}
