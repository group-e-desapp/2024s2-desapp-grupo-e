package com.unq.dapp_grupo_e.modelTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.exceptions.InvalidCharactersException;
import com.unq.dapp_grupo_e.exceptions.InvalidEmailException;
import com.unq.dapp_grupo_e.exceptions.InvalidLengthException;
import com.unq.dapp_grupo_e.factory.UserFactory;
import com.unq.dapp_grupo_e.model.User;


@ActiveProfiles("test") 
@SpringBootTest
class UserTests {


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
        Assertions.assertThrows(InvalidLengthException.class, () -> user.checkPassword("Mark#"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"#asdfg", "#QWERT", "Wasdfg"})
    void exceptionForMissingATypeOfCharacterInPassword(String passwordTry) {
        var user = new User();
        Assertions.assertThrows(InvalidCharactersException.class, () -> user.checkPassword("passwordTry"));
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

    
}
