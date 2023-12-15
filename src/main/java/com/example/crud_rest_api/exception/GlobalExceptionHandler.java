//package com.example.crud_rest_api.exception;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import org.zalando.problem.Problem;
//import org.zalando.problem.Status;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Problem> handleEntityNotFoundException(EntityNotFoundException e) {
//        return ResponseEntity.status(Status.NOT_FOUND.getStatusCode())
//                .body(Problem.builder()
//                        .withTitle("Entity Not Found")
//                        .withDetail(e.getMessage())
//                        .build());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Problem> handleGenericException(Exception e) {
//        return ResponseEntity.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//                .body(Problem.builder()
//                        .withTitle("Internal Server Error")
//                        .withDetail("An unexpected error occurred")
//                        .with("error", "Internal Server Error")
//                        .build());
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Problem> handleUserNotFoundException(UserNotFoundException e) {
//        return ResponseEntity.status(Status.NOT_FOUND.getStatusCode())
//                .body(e.asProblem());
//    }
//
//}