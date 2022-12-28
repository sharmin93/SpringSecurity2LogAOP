package com.example.springlogger.service;

import com.example.springlogger.db.entity.User;
import com.example.springlogger.db.entity.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService{
    User saveUser(User user);

    UserRole saveUserRole(UserRole userRole);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getAllUser();
}
