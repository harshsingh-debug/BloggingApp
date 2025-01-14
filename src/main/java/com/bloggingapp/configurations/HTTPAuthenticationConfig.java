package com.bloggingapp.configurations;

import com.bloggingapp.security.CustomUserDetailService;
import com.bloggingapp.security.JwtAuthenticationEntryPoint;
import com.bloggingapp.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class HTTPAuthenticationConfig {

    private CustomUserDetailService customeUserDetailService;
    private PasswordEncoder passwordEncoder;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public HTTPAuthenticationConfig (CustomUserDetailService customeUserDetailService, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.customeUserDetailService = customeUserDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    //The below code is fetched from spring security doc for reference
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/products/**"))
//                        .permitAll())
//                .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/customers/**"))
//                        .hasRole("ADMIN")
//                        .anyRequest()
//                        .authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }

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
