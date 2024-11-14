package com.unq.dapp_grupo_e.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

//import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
//import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;
//import com.unq.dapp_grupo_e.model.exceptions.DuplicationDataException;
//import com.unq.dapp_grupo_e.model.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository userRepo) {
        this.repo = userRepo;
    }

    @Override
    public void deleteAllUsers() {
        repo.deleteAll();
        repo.resetIdUser();
    }

    @Override
    public User findById(Integer id) {
        var userOptional = repo.findById(id);

        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("The id user given is not valid");
        }
    }
    
}
