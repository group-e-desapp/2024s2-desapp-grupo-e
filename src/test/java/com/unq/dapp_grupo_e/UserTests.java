package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.controller.dto.UserRegisterResponseDTO;
import com.unq.dapp_grupo_e.factories.UserFactory;
import com.unq.dapp_grupo_e.factories.UserRegisterFactory;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.model.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCharactersException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidEmailException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidLengthException;
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
    void exceptionForInvalidShortLengthName() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setName("IA"));
    }

    @Test
    void exceptionForInvalidLongLengthName() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setName("AABBCCDDEEFFGGHHIIJJKKLLMMNNOOP"));
    }

    @Test
    void exceptionForInvalidLengthForCVU() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setCvu("1234567890"));
    }

    @Test
    void exceptionForInvalidLengthForWalletAddress() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setWalletAddress("7654321"));
    }

    @Test
    void exceptionForInvalidLengthForPassword() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setPassword("Mark#"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"#asdfg", "#QWERT", "Wasdfg"})
    void exceptionForMissingATypeOfCharacterInPassword(String passwordTry) {
        var user = new User();
        Assertions.assertThrows(InvalidCharactersException.class, () -> user.setPassword("passwordTry"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mark05com", "#mark05.com" ,"@gmail.com",  "mark05gmail.com"})
    void exceptionForInvalidEmail(String emailTry) {
        var user = new User();
        Assertions.assertThrows(InvalidEmailException.class, () -> user.setEmail(emailTry));
    }
 
    @Test
    void checkReputationOfUser() {
        var userWithOperations = UserFactory.createWithSomeOperations(40, 30);
        Assertions.assertEquals(75, userWithOperations.reputation());
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

    @Test
    void exceptionForDuplicatedEmailUsedForRegister() {
        var userDTO = UserRegisterFactory.createWithEmail("mark5@gmail.com");
        var userDTODuplicated = UserRegisterFactory.createWithEmail("mark5@gmail.com");
        userService.createUser(userDTO);
        Assertions.assertThrows(DuplicationDataException.class, () -> userService.createUser(userDTODuplicated));

    }
    
}
