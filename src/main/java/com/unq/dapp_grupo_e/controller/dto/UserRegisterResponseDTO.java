package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.User;

public class UserRegisterResponseDTO {
    
    private String name;
    private String surname;
    private String email;


    public static UserRegisterResponseDTO from(User user) {
        var userDTO = new UserRegisterResponseDTO();
        userDTO.name = user.getName();
        userDTO.surname = user.getSurname();
        userDTO.email = user.getEmail();

        return userDTO;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
    
}
