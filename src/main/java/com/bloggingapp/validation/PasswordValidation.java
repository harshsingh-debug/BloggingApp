package com.bloggingapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidation implements ConstraintValidator<ValidPassword, String> {
    public static final Pattern BASE_PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z0-9!@#$%&*]{8,15}");
    public static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[!@#$%&*]");

    public PasswordValidation() {
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        } else if (!BASE_PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        } else {
            long specialCharCount = SPECIAL_CHARACTER_PATTERN.matcher(password).results().count();
            return specialCharCount <= 2L;
        }
    }
}
