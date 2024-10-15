package com.unq.dapp_grupo_e.utilities.factories;


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
        return user;
    }

    public static User createWithSomeOperations(Integer operationsSetted, Integer reputationPoints) {
        var user = new User();
        user.setEmail("non@gmail.com");
        user.setPassword("Non&non");
        user.setName("anon");
        user.setSurname("non");
        user.setCvu("1011121314151617181920");
        user.setWalletAddress("12349876");
        user.setAmountSetOperations(operationsSetted);
        user.setReputationPoints(reputationPoints);
        return user;
    }

    public static User createWithIdDataAndOperations(Long idUser, String name, String email,
                                                    Integer operationsSetted, Integer reputationPoints) {
        var user = new User();
        user.setIdUser(idUser);
        user.setName(name);
        user.setSurname("Evans");
        user.setEmail(email);
        user.setPassword("Non&noN");
        user.setCvu("1011121314151617181920");
        user.setWalletAddress("12349876");
        user.setAmountSetOperations(operationsSetted);
        user.setReputationPoints(reputationPoints);
        return user;
    }
    
}
