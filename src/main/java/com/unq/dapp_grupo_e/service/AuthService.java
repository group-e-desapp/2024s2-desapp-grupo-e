package com.unq.dapp_grupo_e.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepo;
    private JwtTokenService jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepo, JwtTokenService jwtTokenProvider, 
        PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public String login(String userEmail, String userPassword) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, userPassword));
        UserDetails user = userRepo.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return jwtTokenProvider.generateToken(user);
    }
    
    public String register(UserRegisterDTO userForm) {
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

        return jwtTokenProvider.generateToken(user);
    }

}
