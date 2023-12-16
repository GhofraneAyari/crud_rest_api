package com.example.crud_rest_api.validation;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,4}$";
        return email.matches(emailRegex);
    }
}
