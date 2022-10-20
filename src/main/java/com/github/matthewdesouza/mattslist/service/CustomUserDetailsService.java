package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.entity.User;
import com.github.matthewdesouza.mattslist.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implements and modifies the loadByUserName function in {@link UserDetailsService}.
 * @author Matthew DeSouza
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user from the {@link org.springframework.security.core.userdetails.User} implementation, and assigns values to it.
     * @param usernameOrEmail {@link String}
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException Username was not found to be within the database.
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(usernameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }
}
