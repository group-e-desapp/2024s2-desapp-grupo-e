package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.service.UserService;

@SpringBootTest
class UserTests {

    @Autowired
    private UserService userService;


    void deleteAndReset() {
        userService.deleteUsers();
        userService.resetIdUser();
    }

    @Test
    void checkUserSaved() {
        deleteAndReset();
        var userDTO = new UserRegisterDTO();
        userDTO.name = "Mark";
        userDTO.surname = "Goldbert";
        userDTO.email = "mark@gmail.com";
        userDTO.password = "Mark00";
        userService.createUser(userDTO);
        var userRecovered = userService.findById(1);
        Assertions.assertEquals(userDTO.email, userRecovered.getEmail());
    }
    
}
