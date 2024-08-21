package com.example.springSecurityCoreLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails doctor = User
                .withUsername("doctor")
                .password(
                        passwordEncoder()
                                .encode("doctor")
                )
                .roles("DOCTOR").
                build();

        UserDetails patient = User
                .withUsername("patient").password(
                        passwordEncoder().encode("patient")
                )
                .roles("PATIENT")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(
                        passwordEncoder().encode("admin")
                )
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(doctor, patient, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(
                        auth -> auth
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                                .requestMatchers("/patient/**").hasRole("PATIENT")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form.defaultSuccessUrl("/home", true)
                )
                .logout(config -> config.logoutUrl("/logout")).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
