package com.example.crud_rest_api;

public final class ApiConstants {

    private ApiConstants() {

    }

    public static final String API_BASE_PATH = "/api/users";
    public static final String GET_ALL_USERS_PATH = "";
    public static final String SLASH = "/";
    public static final String GET_USER_BY_ID_PATH = "/{userId}";
    public static final String UPDATE_USER_PATH = "/{userId}";
    public static final String DELETE_USER_PATH = "/{userId}";
    public static final String GET_ALL_USERS_OPERATION_SUMMARY = "Get all users";
    public static final String GET_ALL_USERS_OPERATION_DESCRIPTION = "Get a list of all users";
    public static final String GET_USER_BY_ID_OPERATION_SUMMARY = "Get user by id";
    public static final String GET_USER_BY_ID_OPERATION_DESCRIPTION = "Get user by id";
    public static final String CREATE_USER_OPERATION_SUMMARY = "Create a new user";
    public static final String CREATE_USER_OPERATION_DESCRIPTION = "Create a new user";
    public static final String UPDATE_USER_OPERATION_SUMMARY = "Update user";
    public static final String UPDATE_USER_OPERATION_DESCRIPTION = "Update user";
    public static final String DELETE_USER_OPERATION_SUMMARY = "Delete user by id";
    public static final String DELETE_USER_OPERATION_DESCRIPTION = "Delete user by id";
    public static final String ALL_USERS_REL = "all-users";
    public static final String UPDATE_USER_REL = "update-user";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,4}$";
    public static final int MAX_NAME_LENGTH = 50;
}

