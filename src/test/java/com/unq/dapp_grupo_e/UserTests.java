package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.model.User;

@SpringBootTest
class UserTests {

    @Test
    void validationUsername() {
        var user = new User();
        user.setName("Mark");
        Assertions.assertEquals("Mark", user.getName());
    }
    
}
