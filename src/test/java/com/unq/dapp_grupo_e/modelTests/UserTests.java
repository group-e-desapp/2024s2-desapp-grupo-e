package com.unq.dapp_grupo_e.modelTests;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.exceptions.DuplicationDataException;
import com.unq.dapp_grupo_e.exceptions.InvalidCharactersException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmailException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmptyFieldException;
import com.unq.dapp_grupo_e.exceptions.InvalidLengthException;
import com.unq.dapp_grupo_e.factory.UserFactory;
import com.unq.dapp_grupo_e.factory.factoriesdto.UserRegisterFactory;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.service.AuthService;
import com.unq.dapp_grupo_e.service.UserService;

@ActiveProfiles("test") 
@SpringBootTest
class UserTests {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authUserService;

    @BeforeEach
    void deleteAndReset() {
        userService.deleteAllUsers();
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
    void exceptionForInvalidShortLengthSurname() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setSurname("IA"));
    }

    @Test
    void exceptionForInvalidLongLengthSurname() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class, () -> user.setSurname("AABBCCDDEEFFGGHHIIJJKKLLMMNNOOP"));
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
    void exceptionForInvalidLengthForEmail() {
        var user = new User();
        Assertions.assertThrows(InvalidLengthException.class,
                                 () -> user.setEmail("aaaaaaemailemailemailemailemailemailemail@gmail.com"));
    }
 
    @Test
    void checkReputationWithoutOperationsDone() {
        var userWithoutOperations = UserFactory.createWithSomeOperations(0, 0);
        Assertions.assertEquals("No operations realized", userWithoutOperations.reputation());
    }

    @Test
    void checkReputationOfUser() {
        var userWithOperations = UserFactory.createWithSomeOperations(8, 40);
        Assertions.assertEquals("5", userWithOperations.reputation());
    }

    @Test
    void checkAddedOperationsForReputationOfUser() {
        var userWithOperations = UserFactory.createWithSomeOperations(4, 45);
        userWithOperations.countANewOperation();
        Assertions.assertEquals("9", userWithOperations.reputation());
    }

    // Service side
 
    @Test
    void checkResponseUserOfRegister() {
        var userDTO = UserRegisterFactory.anyUserRegister();
        var userResponse = authUserService.register(userDTO);
        Assertions.assertTrue(userResponse instanceof String);
    }

    @Test
    void checkUserWasSaved() {
        var userDTO = UserRegisterFactory.anyUserRegister();
        authUserService.register(userDTO);
        Assertions.assertNotNull(userService.findById(1));
    }

    @Test
    void checkUserSavedData() {
        var userDTO = UserRegisterFactory.createWithNameAndSurname("Mark", "Coyle");
        authUserService.register(userDTO);
        var userRecovered = userService.findById(1);
        Assertions.assertEquals("Mark", userRecovered.getName());
        Assertions.assertEquals("Coyle", userRecovered.getSurname());
    }

    @Test
    void exceptionForDuplicatedEmailUsedForRegister() {
        var userDTO = UserRegisterFactory.createWithEmail("mark5@gmail.com");
        var userDTODuplicated = UserRegisterFactory.createWithEmail("mark5@gmail.com");
        authUserService.register(userDTO);
        Assertions.assertThrows(DuplicationDataException.class, () -> authUserService.register(userDTODuplicated));
    }

    @Test
    void exceptionForInvalidEmailForRegister() {
        var userDTO = UserRegisterFactory.createWithEmail("mark5@gmailcom");
        Assertions.assertThrows(InvalidEmailException.class, () -> authUserService.register(userDTO));
    }

    @Test
    void exceptionForMissingCharactersInPasswordForRegister() {
        var userDTO = UserRegisterFactory.createWithPassword("mark05#");
        Assertions.assertThrows(InvalidCharactersException.class, () -> authUserService.register(userDTO));
    }

    @Test
    void exceptionForInvalidLengthInNameForRegister() {
        var userDTO = UserRegisterFactory.createWithName("AI");
        Assertions.assertThrows(InvalidLengthException.class, () -> authUserService.register(userDTO));
    }

    @Test
    void exceptionForEmptyFieldForRegister() {
        var userDTO = UserRegisterFactory.createWithSurname("");
        Assertions.assertThrows(InvalidEmptyFieldException.class, () -> authUserService.register(userDTO));
    }

    @Test
    void exceptionForInvalidUserIdSearched() {
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.findById(3));
    }
    
}
