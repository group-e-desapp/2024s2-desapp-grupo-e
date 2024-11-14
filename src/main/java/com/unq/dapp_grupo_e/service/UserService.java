package com.unq.dapp_grupo_e.service;


import com.unq.dapp_grupo_e.model.User;

public interface UserService {

    //AuthResponse createUser(UserRegisterDTO entity);

    User findById(Integer id);

    void deleteAllUsers();

}
