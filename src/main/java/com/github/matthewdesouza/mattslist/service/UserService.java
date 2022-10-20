package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    List<UserDto> getAllUsers();
    void saveUser(UserDto userDto);
}
