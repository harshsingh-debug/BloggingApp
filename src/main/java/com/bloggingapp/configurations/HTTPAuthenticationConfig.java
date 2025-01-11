package com.bloggingapp.configurations;

import com.bloggingapp.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class HTTPAuthenticationConfig {

    private CustomUserDetailService customeUserDetailService;
    private PasswordEncoder passwordEncoder;

    public HTTPAuthenticationConfig (CustomUserDetailService customeUserDetailService, PasswordEncoder passwordEncoder) {
        this.customeUserDetailService = customeUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(httpBasic -> {});

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(this.customeUserDetailService)
                .passwordEncoder(this.passwordEncoder)
                .and()
                .build();
    }
}
