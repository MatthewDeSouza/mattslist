package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.dto.UserDto;
import com.github.matthewdesouza.mattslist.entity.User;

import java.util.List;

/**
 * Interface of various CRUD operations.
 * @author Matthew DeSouza
 */
public interface UserService {
    /**
     * Gets a {@link User} by username.
     * @param username {@link String}
     * @return {@link User}
     */
    User getUserByUsername(String username);

    /**
     * Returns all {@link User} from the database, mapped to {@link UserDto}
     * @return {@link List}
     */
    List<UserDto> getAllUsers();

    /**
     * Saves a {@link User} to the database.
     * @param userDto {@link UserDto}
     */
    void saveUser(UserDto userDto);
}
