//package com.example.crud_rest_api.exception;
//
//import org.zalando.problem.AbstractThrowableProblem;
//import org.zalando.problem.Status;
//
//import java.net.URI;
//
//public class UserNotFoundException extends RuntimeException {
//
//    public UserNotFoundException(Long userId) {
//        super("User with ID " + userId + " not found");
//    }
//
//    public CustomProblem asProblem() {
//        return new CustomProblem(
//                URI.create(ErrorConstants.DEFAULT_TYPE),
//                "User Not Found",
//                Status.NOT_FOUND,
//                "User not found"
//        );
//    }
//}
