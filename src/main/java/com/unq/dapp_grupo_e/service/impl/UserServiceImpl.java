package com.unq.dapp_grupo_e.service.impl;

import java.util.NoSuchElementException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.model.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository userRepo) {
        this.repo = userRepo;
    }

    @Override
    public UserRegisterResponseDTO createUser(UserRegisterDTO entityUser) {
        if (repo.existsByEmail(entityUser.email)) {
            throw new DuplicationDataException("This email has already been used");
        }
        var user = new User();
        user.setEmail(entityUser.email);
        user.setName(entityUser.name);
        user.setSurname(entityUser.surname);
        user.setPassword(entityUser.password);
        user.setCvu(entityUser.cvu);
        user.setWalletAddress(entityUser.walletAddress);
        repo.save(user);
        return UserRegisterResponseDTO.from(user);
    }

    @Override
    public void deleteUsers() {
        repo.deleteAll();
    }

    @Override
    public void resetIdUser() {
        repo.resetIdUser();
    }

    @Override
    public User findById(Integer id) {
        var userOptional = repo.findById(id);
        try {
            if(userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new NotFoundException();
            }
        }  catch (Exception e) {
            throw new NoSuchElementException("The id user given is not valid");
        }
    }
    
}
