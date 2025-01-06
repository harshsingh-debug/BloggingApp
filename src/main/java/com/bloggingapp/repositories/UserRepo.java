package com.bloggingapp.repositories;

import com.bloggingapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail (String email);
}
