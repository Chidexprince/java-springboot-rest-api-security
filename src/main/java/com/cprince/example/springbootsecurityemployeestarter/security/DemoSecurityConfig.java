package com.cprince.example.springbootsecurityemployeestarter.security;

import com.cprince.example.springbootsecurityemployeestarter.util.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // Add support for JDBC
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, password, active from members where user_id=?");

        // define query to retrieve roles
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
            configurer
                    .requestMatchers(HttpMethod.GET, "api/employees").hasRole(UserRole.EMPLOYEE.name())
                    .requestMatchers(HttpMethod.GET, "api/employees/**").hasRole(UserRole.EMPLOYEE.name())
                    .requestMatchers(HttpMethod.POST, "api/employees").hasRole(UserRole.MANAGER.name())
                    .requestMatchers(HttpMethod.PUT, "api/employees").hasRole(UserRole.MANAGER.name())
                    .requestMatchers(HttpMethod.DELETE, "api/employees/**").hasRole(UserRole.ADMIN.name())
        );

        // use http basic auth
        httpSecurity.httpBasic(Customizer.withDefaults());

        // disable CSRF, use when web app is used
        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

// In memory cache
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails prince = User.builder()
//                .username("prince")
//                .password("{noop}password")
//                .roles(UserRole.ADMIN.name(), UserRole.EMPLOYEE.name())
//                .build();
//
//        UserDetails jane = User.builder()
//                .username("jane")
//                .password("{noop}password")
//                .roles(UserRole.MANAGER.name(), UserRole.EMPLOYEE.name())
//                .build();
//
//        UserDetails marcus = User.builder()
//                .username("marcus")
//                .password("{noop}password")
//                .roles(UserRole.EMPLOYEE.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(prince, jane, marcus);
//    }

}
