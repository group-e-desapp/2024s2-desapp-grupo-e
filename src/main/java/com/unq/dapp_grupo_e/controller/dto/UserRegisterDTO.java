package com.unq.dapp_grupo_e.controller.dto;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    static final String MESSAGEMINMAX = "This field has a limit of 30 characters and a required minimun of 3 characters";

    @NotNull
    @Size(max = 70, message = "Email surpassed the expected length")
    @Email(message = "The given value is not a valid e-mail")
    public String email;

    @Size(min = 6, max = 70, message = "Password must contain at least 6 characters")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least 1 lowercase, 1 uppercase and 1 special character")
    public String password;

    @Size(min = 3, max = 30, message = MESSAGEMINMAX)
    @NotNull
    public String name;

    @Size(min = 3, max = 30, message = MESSAGEMINMAX)
    @NotNull
    public String surname;

    @Size(min = 22, max = 22)
    @NotNull
    public String cvu;

    @Size(min = 8, max = 8)
    @NotNull
    public String walletAddress;

    public boolean validationOfEmptyFields() {
        List<String> fields = Arrays.asList(this.name, this.surname, this.email, 
                                            this.password, this.cvu, this.walletAddress);
        return fields.stream().anyMatch(f -> f == null || f.isEmpty()); // 
    }

}