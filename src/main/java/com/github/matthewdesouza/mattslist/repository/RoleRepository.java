package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);}
