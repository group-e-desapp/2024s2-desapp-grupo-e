package com.unq.dapp_grupo_e.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.controller.dto.AuthResponse;
import com.unq.dapp_grupo_e.controller.dto.LoginRequest;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users", description = "Methods for users of CryptoAPI")
@RestController
@RequestMapping("/authUser")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @ApiResponse(responseCode = "201", description = "New user registered")
    @ApiResponse(responseCode = "400", description = "Invalid required data was given", content = @Content)
    @Operation(summary = "Register a new user",
               description = "Register a new user with the described data in the form fields")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDTO userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }

}
