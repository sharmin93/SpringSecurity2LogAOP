package com.example.springlogger.service;

import com.example.springlogger.controller.UserController;
import com.example.springlogger.db.entity.User;
import com.example.springlogger.db.entity.UserRole;
import com.example.springlogger.db.repository.RoleRepository;
import com.example.springlogger.db.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Data
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    static Logger logger = Logger.getLogger(UserController.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        logger.info("saving user to database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        logger.info("saving userRole to Database");
        return roleRepository.save(userRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        UserRole userRole = roleRepository.findByName(roleName);
        logger.info("saving userRole, userRoleName");
        user.getRoles().add(userRole);
    }

    @Override
    public User getUser(String username) {
        logger.info("getting userName");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        logger.info("get All user");
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("user is not valid");
            throw new UsernameNotFoundException("user is not valid");
        } else {
            logger.info("username: " + username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUsername(), authorities);
    }
}
