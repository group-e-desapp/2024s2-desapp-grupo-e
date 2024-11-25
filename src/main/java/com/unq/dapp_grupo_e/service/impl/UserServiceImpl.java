package com.unq.dapp_grupo_e.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository userRepo) {
        this.repo = userRepo;
    }

    public UserRegisterResponseDTO registerUser(UserRegisterDTO userForm) {
        if (userForm.validationOfEmptyFields()) {
            throw new InvalidEmptyFieldException("There is a missing required field");
        }
        if (repo.existsByEmail(userForm.email)) {
            throw new DuplicationDataException("This email has already been used");
        }
        var user = new User();
        user.setEmail(userForm.email);
        user.setName(userForm.name);
        user.setSurname(userForm.surname);
        user.setPassword(userForm.password);
        user.setPassword(userForm.password);
        user.setCvu(userForm.cvu);
        user.setWalletAddress(userForm.walletAddress);
        user.setRole(Role.USER);
        repo.save(user);
        return UserRegisterResponseDTO.from(user);
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
