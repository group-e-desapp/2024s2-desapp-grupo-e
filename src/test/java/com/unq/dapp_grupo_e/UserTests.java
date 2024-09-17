package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.factories.UserRegisterFactory;
import com.unq.dapp_grupo_e.service.UserService;

@SpringBootTest
class UserTests {

    @Autowired
    private UserService userService;

    @BeforeEach
    void deleteAndReset() {
        userService.deleteUsers();
        userService.resetIdUser();
    }

    @Test
    void checkResponseUserOfRegister() {
        var userDTO = UserRegisterFactory.anyUserRegister();
        var userResponse = userService.createUser(userDTO);
        Assertions.assertTrue(userResponse instanceof UserRegisterResponseDTO);
    }

    @Test
    void checkUserWasSaved() {
        var userDTO = UserRegisterFactory.anyUserRegister();
        userService.createUser(userDTO);
        Assertions.assertNotNull(userService.findById(1));
    }

    @Test
    void checkUserSavedData() {
        var userDTO = UserRegisterFactory.createWithNameAndSurname("Mark", "Coyle");
        userService.createUser(userDTO);
        var userRecovered = userService.findById(1);
        Assertions.assertEquals("Mark", userRecovered.getName());
        Assertions.assertEquals("Coyle", userRecovered.getSurname());
    }
    
}
