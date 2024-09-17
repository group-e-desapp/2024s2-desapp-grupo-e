package com.unq.dapp_grupo_e.factories;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;

public class UserRegisterFactory {

    public static UserRegisterDTO anyUserRegister() {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Nonnon";
        userDTO.name = "anon";
        userDTO.surname = "non";
        return userDTO;
    }

    public static UserRegisterDTO createWithNameAndSurname(
        String name, String surname
    ) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Nonnon";
        userDTO.name = name;
        userDTO.surname = surname;
        return userDTO;
    }
    
}
