package com.bloggingapp.repositories;

import com.bloggingapp.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Integer> {
}
