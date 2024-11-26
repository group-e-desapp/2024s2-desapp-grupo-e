package com.unq.dapp_grupo_e.dto;

import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    static final String MESSAGEMINMAX = "This field has a limit of 30 characters and a required minimun of 3 characters";

    @NotNull
    @Schema(example = "dapp@gmail.com")
    @Size(max = 50, message = "Email surpassed the expected length")
    @Email(message = "The given value is not a valid e-mail")
    public String email;

    @NotNull
    @Schema(example = "Non&noN")
    @Size(min = 6, max = 50, message = "Password must contain at least 6 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least 1 lowercase, 1 uppercase and 1 special character")
    public String password;

    @NotNull
    @Schema(example = "Mark")
    @Size(min = 3, max = 30, message = MESSAGEMINMAX)
    public String name;

    @NotNull
    @Schema(example = "Evans")
    @Size(min = 3, max = 30, message = MESSAGEMINMAX)
    public String surname;

    @NotNull
    @Schema(example = "1546797956231484564523")
    @Size(min = 22, max = 22)
    public String cvu;

    @NotNull
    @Schema(example = "AF151358")
    @Size(min = 8, max = 8)
    public String walletAddress;

    public boolean validationOfEmptyFields() {
        List<String> fields = Arrays.asList(this.name, this.surname, this.email, 
                                            this.password, this.cvu, this.walletAddress);
        return fields.stream().anyMatch(f -> f == null || f.isEmpty()); // 
    }

    @Override
    public String toString() {
        return String.format("UserRegisterDTO{name='%s', surname='%s', email='%s', cvu='%s', walletAddress='%s'}",
                            name, surname, email, cvu, walletAddress);
    }

}