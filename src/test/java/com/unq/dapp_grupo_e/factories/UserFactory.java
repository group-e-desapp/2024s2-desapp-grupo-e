package com.unq.dapp_grupo_e.factories;


import com.unq.dapp_grupo_e.model.User;

public class UserFactory {


    public static User anyUser() {
        var user = new User();
        user.setEmail("non@gmail.com");
        user.setPassword("Non&non");
        user.setName("anon");
        user.setSurname("non");
        user.setCvu("1011121314151617181920");
        user.setWalletAddress("12349876");
        user.countANewOperation();
        user.countASucceddedOperation();
        return user;
    }

    public static User createWithSomeOperations(Integer operationsSetted, Integer operationsSuccedded) {
        var user = new User();
        user.setEmail("non@gmail.com");
        user.setPassword("Non&non");
        user.setName("anon");
        user.setSurname("non");
        user.setCvu("1011121314151617181920");
        user.setWalletAddress("12349876");
        user.setAmountSetOperations(operationsSetted);
        user.setAmountSuccededOperations(operationsSuccedded);
        return user;
    }
    
}
