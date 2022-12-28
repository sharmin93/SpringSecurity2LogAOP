package com.example.springlogger.controller;

import com.example.springlogger.db.entity.User;
import com.example.springlogger.db.entity.UserRole;
import com.example.springlogger.db.repository.RoleRepository;
import com.example.springlogger.db.repository.UserRepository;
import com.example.springlogger.model.request.RoleAddUserRequest;
import com.example.springlogger.model.request.UerByNameRequest;
import com.example.springlogger.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    static Logger logger = Logger.getLogger(UserController.class);

    @GetMapping(value = "/welcome")
    public String welcome() {
        String message = "welcome";
        logger.debug("debug message");
        logger.info("info message");
        return message;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));

    }

    @PostMapping(value = "/user/userRole")
    public ResponseEntity<UserRole> saveUserRole(@RequestBody UserRole role) {
        return ResponseEntity.ok().body(userService.saveUserRole(role));
    }


    @PostMapping(value = "/user/roleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleAddUserRequest roleAddUserRequest) {
        userService.addRoleToUser(roleAddUserRequest.getUserName(), roleAddUserRequest.getUserName());
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/user/allUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }


}
