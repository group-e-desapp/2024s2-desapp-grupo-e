package com.unq.dapp_grupo_e.utilities;

import java.util.regex.Pattern;

public class CharacterValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=<>|?°]).+$");

    public static boolean validateEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
}
