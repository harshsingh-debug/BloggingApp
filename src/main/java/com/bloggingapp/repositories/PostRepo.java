package com.bloggingapp.repositories;

import com.bloggingapp.dto.PostDto;
import com.bloggingapp.entity.CategoryEntity;
import com.bloggingapp.entity.PostEntity;
import com.bloggingapp.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUser(UserEntity userEntity);

    List<PostEntity> findByCategory(CategoryEntity categoryEntity);

//    List<PostEntity> findByPostTitleContains(String title);

    @Query("SELECT p FROM PostEntity p WHERE p.postTitle LIKE :key")
    List<PostEntity> searchByKeyword (@Param("key") String keyword);
}
