package com.github.matthewdesouza.mattslist.service.impl;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.Role;
import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.repository.RoleRepository;
import com.github.matthewdesouza.mattslist.repository.UserRepository;
import com.github.matthewdesouza.mattslist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link UserService}.
 *
 * @author Matthew DeSouza
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Finds {@link User} by username.
     * @param username {@link String}
     * @return {@link User}
     */
    @Override
    public User getUserByUsername(String username) {
        log.info("Getting User by username (username={}).", username);
        return userRepository.findUserByUsername(username);
    }

    /**
     * Gets all users from the database.
     * @return {@link List}
     */
    @Override
    public List<UserDto> getAllUsers() {
        log.info("Getting all users.");
        List<User> users = userRepository.findAll();
         return users
                 .stream()
                 .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    return userDto;
                }).toList();
    }

    /**
     * Saves a user to the database. Needs to be manually changed for now to assign a particular role during registration.
     * @param userDto {@link UserDto}
     */
    @Override
    public void saveUser(UserDto userDto) {
        log.info("Saving user (username={}).", userDto.getUsername());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        Role role = roleRepository.findRoleByName("ROLE_USER");
        if (role == null) {
            Role newRole = new Role();
            newRole.setName("ROLE_USER");
            role = roleRepository.save(newRole);
        }
        user.setRoles(Stream.of(role).collect(Collectors.toSet()));
        userRepository.save(user);
    }

    /**
     * Saves a user to the database.
     * @param user {@link User}
     */
    @Override
    public void saveUser(User user) {
        log.info("Saving User (username={}).", user.getUsername());
        userRepository.save(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        log.info("Deleting User (username={}).", username);
        User user = userRepository.findUserByUsername(username);
        for (Role role : user.getRoles()) {
            role.removeUser(user);
        }
        userRepository.deleteUserByUsername(user.getUsername());
    }
}
