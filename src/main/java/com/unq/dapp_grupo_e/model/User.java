package com.unq.dapp_grupo_e.model;

import com.unq.dapp_grupo_e.model.exceptions.InvalidCharactersException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidEmailException;
import com.unq.dapp_grupo_e.model.exceptions.InvalidLengthException;
import com.unq.dapp_grupo_e.utilities.CharacterValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String cvu;
    @Column
    private String walletAddress;
    
    @Column
    private Integer amountSetOperations = 0;
    @Column
    private Integer reputationPoints = 0;

    public User() {};

    public User(Long id, String name, String surname, String email, String password, 
                String cvu, String walletAddress, Integer amountOperations, Integer reputation) {
        this.setIdUser(id);
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setPassword(password);
        this.setCvu(cvu);
        this.setWalletAddress(walletAddress);
        this.setAmountSetOperations(amountOperations);
        this.setReputationPoints(reputation);
    }

    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getCvu() {
        return cvu;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setEmail(String email) {
        if (email.length() > 50) {
            throw new InvalidLengthException("The email given is not valid due to its extension");
        }
        if (!CharacterValidator.validateEmail(email)) {
            throw new InvalidEmailException("The email given is not valid");
        }
        this.email = email;
    }
    
    public void setName(String name) {
        if (name.length() < 3 || name.length() > 30) {
            throw new InvalidLengthException("The name given need to have at least 3 characters and 30 at most");
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if (surname.length() < 3 || surname.length() > 30) {
            throw new InvalidLengthException("The surname given need to have at least 3 characters and 30 at most");
        }
        this.surname = surname;
    }
    
    public void setPassword(String password) {
        if (password.length() < 6) {
            throw new InvalidLengthException("The password given need to have at least 6 characters");
        }
        if (!CharacterValidator.validatePassword(password)) {
            throw new InvalidCharactersException("The password need to have at least 1 lowercase, 1 uppercase and 1 special character");
        }
        this.password = password;
    }
    
    public void setCvu(String cvu) {
        if (cvu.length() != 22) {
            throw new InvalidLengthException("The cvu given doesn't meet the requirement of 22 digits");
        }
        this.cvu = cvu;
    }
    
    public void setWalletAddress(String walletAddress) {
        if (walletAddress.length() != 8) {
            throw new InvalidLengthException("The wallet address given doesn't meet the requirement of 8 digits");
        }
        this.walletAddress = walletAddress;
    }

    
    public void setAmountSetOperations(Integer amountSetOperations) {
        this.amountSetOperations = amountSetOperations;
    }

    public void setReputationPoints(Integer reputationPoints) {
        this.reputationPoints = reputationPoints;
    }
    


    public void countANewOperation() {
        this.amountSetOperations += 1;
    }

    public String reputation() {
        if (this.amountSetOperations == 0) {
            return "No operations realized";
        } else {
            Integer resultReputation = this.reputationPoints / this.amountSetOperations;
            return resultReputation.toString();
        }
    }
    

    
    
}
