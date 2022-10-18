package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Role;
import com.github.matthewdesouza.springforum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Set<User> findUserByRole(Role role);
}
