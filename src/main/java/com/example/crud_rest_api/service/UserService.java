package com.example.crud_rest_api.service;

import com.example.crud_rest_api.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
