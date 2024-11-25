package com.unq.dapp_grupo_e.service;


import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;

public interface UserService {

    UserRegisterResponseDTO registerUser(UserRegisterDTO entity);

    User findById(Integer id);

    void deleteAllUsers();

}
