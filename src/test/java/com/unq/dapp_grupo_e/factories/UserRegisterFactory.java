package com.unq.dapp_grupo_e.factories;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;

public class UserRegisterFactory {

    public static UserRegisterDTO anyUserRegister() {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Non&non";
        userDTO.name = "anon";
        userDTO.surname = "non";
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithName(String name) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Non&non";
        userDTO.name = name;
        userDTO.surname = "non";
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithSurname(String surname) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Non&non";
        userDTO.name = "anon";
        userDTO.surname = surname;
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithPassword(String password) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = password;
        userDTO.name = "anon";
        userDTO.surname = "non";
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithEmail(String email) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = email;
        userDTO.password = "Non&non";
        userDTO.name = "anon";
        userDTO.surname = "non";
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithNameAndSurname(
        String name, String surname
    ) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non@gmail.com";
        userDTO.password = "Non&non";
        userDTO.name = name;
        userDTO.surname = surname;
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "12349876";
        return userDTO;
    }

}
