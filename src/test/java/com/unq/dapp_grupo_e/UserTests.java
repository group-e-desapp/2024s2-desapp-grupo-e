package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.model.User;

@SpringBootTest
public class UserTests {

    @Test
    void validationUsername() {
        var user = new User();
        user.setNombre("Mark");
        Assertions.assertEquals("Mark", user.getNombre());
    }
    
}
