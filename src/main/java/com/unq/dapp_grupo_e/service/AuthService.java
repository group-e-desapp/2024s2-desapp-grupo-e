package com.unq.dapp_grupo_e.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.AuthResponse;
import com.unq.dapp_grupo_e.controller.dto.LoginRequest;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.model.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.model.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepo;
    private JwtTokenService jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepo, JwtTokenService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
    }


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepo.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        String token = jwtTokenProvider.generateToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }
    
    public AuthResponse register(UserRegisterDTO userForm) {
        if (userForm.validationOfEmptyFields()) {
            throw new InvalidEmptyFieldException("There is a missing required field");
        }
        if (userRepo.existsByEmail(userForm.email)) {
            throw new DuplicationDataException("This email has already been used");
        }
        var user = new User();
        user.setEmail(userForm.email);
        user.setName(userForm.name);
        user.setSurname(userForm.surname);
        user.setPassword(userForm.password);
        user.setPassword(passwordEncoder.encode(userForm.password));
        user.setCvu(userForm.cvu);
        user.setWalletAddress(userForm.walletAddress);
        user.setRole(Role.USER);
        userRepo.save(user);

        return AuthResponse.builder()
            .token(jwtTokenProvider.generateToken(user))
            .build();
    }

}
