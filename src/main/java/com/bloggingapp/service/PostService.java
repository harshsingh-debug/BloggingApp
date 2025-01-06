package com.bloggingapp.service;

import com.bloggingapp.dto.PostDto;
import com.bloggingapp.model.response.PostPaginationResponse;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    PostPaginationResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

    PostDto getPostById(Integer postId);

    String deletePost(Integer postId);

    List<PostDto> getAllPostsByUser(Integer userId);

    List<PostDto> getAllPostsByCategory(Integer categoryId);

    List<PostDto> searchPost(String keyword);
}
