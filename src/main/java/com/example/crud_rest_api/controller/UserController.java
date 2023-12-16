package com.example.crud_rest_api.controller;
import com.example.crud_rest_api.ApiConstants;
import com.example.crud_rest_api.domain.User;
import com.example.crud_rest_api.service.UserService;
import com.example.crud_rest_api.validation.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(path = {ApiConstants.GET_ALL_USERS_PATH, ApiConstants.SLASH})
    @Operation(summary = ApiConstants.GET_ALL_USERS_OPERATION_SUMMARY, description = ApiConstants.GET_ALL_USERS_OPERATION_DESCRIPTION)
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    public ResponseEntity<List<EntityModel<User>>> getAllUsers() {
        List<EntityModel<User>> users = userService.getAllUsers()
                .stream()
                .map(this::addLinks)
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = {ApiConstants.GET_USER_BY_ID_PATH, ApiConstants.GET_USER_BY_ID_PATH+ApiConstants.SLASH})
    @Operation(summary = ApiConstants.GET_USER_BY_ID_OPERATION_SUMMARY, description = ApiConstants.GET_USER_BY_ID_OPERATION_DESCRIPTION)
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userId);
            }

            return new ResponseEntity<>(addLinks(user), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = ApiConstants.CREATE_USER_OPERATION_SUMMARY, description = ApiConstants.CREATE_USER_OPERATION_DESCRIPTION+ApiConstants.SLASH)
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        if (!userValidator.isValidUser(user)) {
            return ResponseEntity.badRequest().build();
        }

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(addLinks(createdUser), HttpStatus.CREATED);
    }

    @PutMapping(path = {ApiConstants.UPDATE_USER_PATH, ApiConstants.UPDATE_USER_PATH+ApiConstants.SLASH})
    @Operation(summary = ApiConstants.UPDATE_USER_OPERATION_SUMMARY, description = ApiConstants.UPDATE_USER_OPERATION_DESCRIPTION)
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long userId, @RequestBody User user) {
        if (!userValidator.isValidUser(user)) {
            return ResponseEntity.badRequest().build();
        }

        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(addLinks(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = {ApiConstants.DELETE_USER_PATH, ApiConstants.DELETE_USER_PATH+ApiConstants.SLASH})
    @Operation(summary = ApiConstants.DELETE_USER_OPERATION_SUMMARY, description = ApiConstants.DELETE_USER_OPERATION_DESCRIPTION)
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EntityModel<User> addLinks(User user) {
        EntityModel<User> userModel = EntityModel.of(user);
        WebMvcLinkBuilder linkToUser = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserById(user.getId()));
        userModel.add(linkToUser.withSelfRel());
        userModel.add(linkToUser.withRel(ApiConstants.UPDATE_USER_REL));
        userModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getAllUsers())
                .withRel(ApiConstants.ALL_USERS_REL));
        return userModel;
    }
}
