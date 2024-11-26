package com.unq.dapp_grupo_e.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.dto.LoginRequest;
import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Methods for users of CryptoAPI")
@RequestMapping("/user")
@RestController
public class UserControllerRest {

    private final UserService userService;

    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(responseCode = "200", description = "Successfull login")
    @ApiResponse(responseCode = "404", content = @Content)
    @Operation(summary = "Login with user credentials",
               description = "Log with your specific email and password")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String tokenGenerated = userService.login(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGenerated);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }


    @ApiResponse(responseCode = "201", description = "New user registered")
    @ApiResponse(responseCode = "400", description = "Invalid required data was given", content = @Content)
    @Operation(summary = "Register a new user",
               description = "Register a new user with the described data in the form fields")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> createUser(@RequestBody UserRegisterDTO user) {
        var response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }
    
}
