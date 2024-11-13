package com.unq.dapp_grupo_e.utilities.factories;


import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;

public class UserFactory {

    private UserFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static User anyUser() {
        var user = new User();
        user.setEmail("non1@gmail.com");
        user.setPassword("Non@noN");
        user.setName("Anon");
        user.setSurname("Non");
        user.setCvu("1011121314151617181920");
        user.setWalletAddress("AA342341");
        user.setRole(Role.USER);
        return user;
    }

    public static User createWithSomeOperations(Integer operationsSetted, Integer reputationPoints) {
        var user = new User();
        user.setEmail("non2@gmail.com");
        user.setPassword("Non#noN");
        user.setName("Nona");
        user.setSurname("Unon");
        user.setCvu("1011121314151617182021");
        user.setWalletAddress("BB342341");
        user.setAmountSetOperations(operationsSetted);
        user.setReputationPoints(reputationPoints);
        user.setRole(Role.USER);
        return user;
    }

    public static User createWithIdDataAndOperations(Integer idUser, String name, String email,
                                                    Integer operationsSetted, Integer reputationPoints) {
        var user = new User();
        user.setIdUser(idUser);
        user.setName(name);
        user.setSurname("Evans");
        user.setEmail(email);
        user.setPassword("Non&noN");
        user.setCvu("1011121314151617182122");
        user.setWalletAddress("CC342341");
        user.setAmountSetOperations(operationsSetted);
        user.setReputationPoints(reputationPoints);
        user.setRole(Role.USER);
        return user;
    }
    
}
