package com.unq.dapp_grupo_e.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.dto.LoginRequest;
import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.security.JwtTokenProvider;
import com.unq.dapp_grupo_e.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, @Lazy PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserRegisterResponseDTO registerUser(UserRegisterDTO userForm) {
        if (userForm.validationOfEmptyFields()) {
            throw new InvalidEmptyFieldException("There is a missing required field");
        }
        if (userRepo.existsByEmail(userForm.email)) {
            throw new DuplicationDataException("This email has already been used");
        }
        var user = new User();
        user.checkPassword(userForm.password);
        
        user.setEmail(userForm.email);
        user.setName(userForm.name);
        user.setSurname(userForm.surname);
        user.setPassword(passwordEncoder.encode(userForm.password));
        user.setCvu(userForm.cvu);
        user.setWalletAddress(userForm.walletAddress);
        user.setRole(Role.USER);
        userRepo.save(user);
        return UserRegisterResponseDTO.from(user);
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }

        return jwtTokenProvider.generateToken(loginRequest.getEmail());
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
        userRepo.resetIdUser();
    }

    @Override
    public User findById(Integer id) {
        var userOptional = userRepo.findById(id);

        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("The id user given is not valid");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with given email"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
    
}
