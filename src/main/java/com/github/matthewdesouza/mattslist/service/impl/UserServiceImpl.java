package com.github.matthewdesouza.mattslist.service.impl;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.Role;
import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.repository.RoleRepository;
import com.github.matthewdesouza.mattslist.repository.UserRepository;
import com.github.matthewdesouza.mattslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
         return users
                 .stream()
                 .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    return userDto;
                }).toList();
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        Role role = roleRepository.findRoleByName("ROLE_ADMIN");
        if (role == null) {
            Role newRole = new Role();
            newRole.setName("ROLE_ADMIN");
            role = roleRepository.save(newRole);
        }
        user.setRoles(Stream.of(role).collect(Collectors.toSet()));
        userRepository.save(user);
    }


}
