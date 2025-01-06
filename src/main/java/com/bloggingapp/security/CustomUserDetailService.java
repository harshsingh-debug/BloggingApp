package com.bloggingapp.security;

import com.bloggingapp.entity.UserEntity;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = this.userRepo.findByEmail(username).orElseThrow(() -> new DataNotFoundException(0, "Data not found for email : " + username));
        return userEntity;
    }
}
