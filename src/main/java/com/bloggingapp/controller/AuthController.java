package com.bloggingapp.controller;

import com.bloggingapp.security.CustomUserDetailService;
import com.bloggingapp.security.JwtTokenHelper;
import com.bloggingapp.security.request.JwtAuthRequest;
import com.bloggingapp.security.response.JwtAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
public class AuthController {


    private AuthenticationManager authenticationManager;
    private JwtTokenHelper jwtTokenHelper;
    private CustomUserDetailService customUserDetailService;

    public AuthController (AuthenticationManager authenticationManager, JwtTokenHelper jwtTokenHelper, CustomUserDetailService customUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenHelper = jwtTokenHelper;
        this.customUserDetailService = customUserDetailService;
    }

    @PostMapping ("/login")
    public ResponseEntity<JwtAuthResponse> createToken (@RequestBody JwtAuthRequest jwtAuthRequest) {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtAuthRequest.getUsername());
        String jwtToken = this.jwtTokenHelper.generateNewToken(userDetails);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
    }

    public void authenticate (String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        this.authenticationManager.authenticate(authenticationToken);
    }

}
