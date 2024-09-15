package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.User;

public class UserRegisterResponseDTO {

    public String name;
    public String surname;
    public String email;


    public static UserRegisterResponseDTO from(User user) {
        var userDTO = new UserRegisterResponseDTO();
        userDTO.name = user.getName();
        userDTO.surname = user.getSurname();
        userDTO.email = user.getEmail();

        return userDTO;
    }    
    
}
