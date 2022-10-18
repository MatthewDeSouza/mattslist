package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    void deleteRoleByName(String name);
}
