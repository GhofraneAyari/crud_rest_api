package com.example.crud_rest_api.validation;

import com.example.crud_rest_api.ApiConstants;
import com.example.crud_rest_api.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean isValidUser(User user) {
        return isValidEmail(user.getEmail()) &&
                isNonNegativeAge(user.getAge()) &&
                isValidName(user.getFirstName()) &&
                isValidName(user.getLastName());
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches(ApiConstants.EMAIL_REGEX);
    }

    public boolean isNonNegativeAge(int age) {
        return age >= 0;
    }

    public boolean isValidName(String name) {
        return name != null && name.length() <= ApiConstants.MAX_NAME_LENGTH;
    }
}
