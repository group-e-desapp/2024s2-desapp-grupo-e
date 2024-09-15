package com.unq.dapp_grupo_e.service;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;

public interface UserService {

    UserRegisterResponseDTO createUser(UserRegisterDTO entity);

}
