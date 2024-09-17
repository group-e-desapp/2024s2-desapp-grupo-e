package com.unq.dapp_grupo_e.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    final static String messageMinMax = "This field has a limit of 30 characters and a required minimun of 3 characters";

    @NotNull
    @Email(message = "The given value is not a valid e-mail")
    public String email;

    @Size(min = 6, message = "Password must contain at least 6 characters")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least one lowercase and one uppercase")
    public String password;

    @Size(min = 3, max = 30, message = messageMinMax)
    @NotNull
    public String name;

    @Size(min = 3, max = 30, message = messageMinMax)
    @NotNull
    public String surname;

    
}