package com.unq.dapp_grupo_e.utilities.factories;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;

public class UserRegisterFactory {

    private UserRegisterFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static UserRegisterDTO anyUserRegister() {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non1@gmail.com";
        userDTO.password = "Non&non";
        userDTO.name = "Anon";
        userDTO.surname = "Non";
        userDTO.cvu = "1011121314151617181920";
        userDTO.walletAddress = "AA349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithName(String name) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non2@gmail.com";
        userDTO.password = "Non@non";
        userDTO.name = name;
        userDTO.surname = "Evans";
        userDTO.cvu = "2011121314151617181920";
        userDTO.walletAddress = "BB349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithSurname(String surname) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non3@gmail.com";
        userDTO.password = "Non#non";
        userDTO.name = "Mako";
        userDTO.surname = surname;
        userDTO.cvu = "3011121314151617181920";
        userDTO.walletAddress = "CC349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithPassword(String password) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non4@gmail.com";
        userDTO.password = password;
        userDTO.name = "Mark";
        userDTO.surname = "Blaze";
        userDTO.cvu = "4011121314151617181920";
        userDTO.walletAddress = "DD349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithEmail(String email) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = email;
        userDTO.password = "Non!non";
        userDTO.name = "Celes";
        userDTO.surname = "Woods";
        userDTO.cvu = "5011121314151617181920";
        userDTO.walletAddress = "EE349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithNameAndSurname(
        String name, String surname
    ) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non6@gmail.com";
        userDTO.password = "Non<>non";
        userDTO.name = name;
        userDTO.surname = surname;
        userDTO.cvu = "6011121314151617181920";
        userDTO.walletAddress = "FF349876";
        return userDTO;
    }

    public static UserRegisterDTO createWithCVU(String cvu) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non7@gmail.com";
        userDTO.password = "Nonnon!";
        userDTO.name = "Name";
        userDTO.surname = "Less";
        userDTO.cvu = cvu;
        userDTO.walletAddress = "GG349876";
        return userDTO;
    }

    public static UserRegisterDTO createWitWalletAddress(String walletAddress) {
        var userDTO = new UserRegisterDTO();
        userDTO.email = "non8@gmail.com";
        userDTO.password = "N!onnon";
        userDTO.name = "Path";
        userDTO.surname = "Surname";
        userDTO.cvu = "6011121314151617181932";
        userDTO.walletAddress = walletAddress;
        return userDTO;
    }

}
