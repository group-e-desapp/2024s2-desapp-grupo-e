package com.unq.dapp_grupo_e.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.unq.dapp_grupo_e.dto.LoginRequest;
import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;

public interface UserService extends UserDetailsService {

    UserRegisterResponseDTO registerUser(UserRegisterDTO entity);

    String login(LoginRequest loginRequest);

    User findById(Integer id);

    void deleteAllUsers();

}
