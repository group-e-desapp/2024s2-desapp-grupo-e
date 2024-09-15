package com.unq.dapp_grupo_e.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserRegisterResponseDTO createUser(UserRegisterDTO entityUser) {
        var user = new User();
        user.setEmail(entityUser.email);
        user.setName(entityUser.name);
        user.setSurname(entityUser.surname);
        user.setPassword(entityUser.password);
        repo.save(user);
        return UserRegisterResponseDTO.from(user);
    }
    
}
