package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for interacting with {@link JpaRepository} for the {@link User} model
 * @author Matthew DeSouza
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds {@link User} by username.
     * @param username {@link String}
     * @return {@link User}
     */
    User findUserByUsername(String username);
}