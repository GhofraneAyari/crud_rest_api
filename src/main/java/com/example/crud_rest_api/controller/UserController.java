package com.example.crud_rest_api.controller;

import com.example.crud_rest_api.domain.User;
import com.example.crud_rest_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Get a list of all users")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    public ResponseEntity<List<EntityModel<User>>> getAllUsers() {
        List<EntityModel<User>> users = userService.getAllUsers().stream()
                .map(this::addLinks)
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Get user by id")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(addLinks(user), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user")
    public ResponseEntity<EntityModel<User>> createUser(@Valid @RequestBody User user) {
        // Check format
        if (!isValidEmail(user.getEmail())) {
            // 400  invalid email format
            return ResponseEntity.badRequest().build();
        }

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(addLinks(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "update user", description = "update user")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(addLinks(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User by id", description = "Delete user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EntityModel<User> addLinks(User user) {
        EntityModel<User> userModel = EntityModel.of(user);
        WebMvcLinkBuilder linkToUser = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId())
        );
        userModel.add(linkToUser.withSelfRel());
        userModel.add(linkToUser.withRel("update-user"));
        userModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()
        ).withRel("all-users"));
        return userModel;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,4}$";
        return email.matches(emailRegex);
    }
}

