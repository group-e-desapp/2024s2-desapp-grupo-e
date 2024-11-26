package com.unq.dapp_grupo_e.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @Schema(example = "dapp@gmail.com")
    String email;
    
    @NotNull
    @Schema(example = "Non&noN")
    String password;

    @Override
    public String toString() {
        return String.format("LoginRequest{email='%s', password=******}", email);
    }
    
}
