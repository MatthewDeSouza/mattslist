package com.github.matthewdesouza.mattslist.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Utilizes the latest Spring Security protocols to manage users, access, and password storage.
 * @author Matthew DeSouza
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    /**
     * Creates a {@link SecurityFilterChain} object in order to manage user logins, as well as access
     * to various parts of the project.
     * @param httpSecurity
     * @return {@link SecurityFilterChain}
     * @throws Exception Any exception that may happen as a result of security management.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Generating Security Filter Chain");
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/topics/create").hasRole(ADMIN)
                .antMatchers("/topics/delete/**").hasRole(ADMIN)
                .antMatchers("/topics/**/posts/**/create").hasAnyRole(USER, ADMIN)
                .antMatchers("/topics/**/posts/**").permitAll()
                .antMatchers("/topics/**").permitAll()
                .antMatchers("/users").hasRole(ADMIN)
                .antMatchers("/user").hasAnyRole(USER, ADMIN)
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/user", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return httpSecurity.build();
    }

    /**
     * Creates a {@link PasswordEncoder} to encrypt passwords within the database.
     * @return passwordEncoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
