package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for interacting with {@link JpaRepository} for the {@link Role} model
 * @author Matthew DeSouza
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds {@link Role} by Name
     * @param name {@link String}
     * @return {@link Role}
     */
    Role findRoleByName(String name);}
