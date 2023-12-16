package com.example.crud_rest_api.exception;
import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class UserNotFoundException extends AbstractThrowableProblem {

    public UserNotFoundException(String message) {
        super(URI.create(message));
    }
}
