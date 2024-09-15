package com.unq.dapp_grupo_e.service;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.model.User;

public interface UserService {

    UserRegisterResponseDTO createUser(UserRegisterDTO entity);

    User findById(Integer id);

    void deleteUsers();

    void resetIdUser();

}
