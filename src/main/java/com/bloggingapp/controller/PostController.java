package com.bloggingapp.controller;

import com.bloggingapp.dto.PostDto;
import com.bloggingapp.model.response.PostPaginationResponse;
import com.bloggingapp.service.PostService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/post"})
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping({"/createPost/user/{userId}/category/{categoryId}"})
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        PostDto postDtoResponse = this.postService.createPost(postDto, userId, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDtoResponse);
    }

    @GetMapping({"/getPost/{postId}"})
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
        PostDto postDtoResponse = this.postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoResponse);
    }

    @GetMapping({"/getPost"})
    public ResponseEntity<PostPaginationResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize, @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy) {
        PostPaginationResponse postPaginationResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(postPaginationResponse);
    }

    @GetMapping({"/getPost/category/{categoryId}"})
    public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<PostDto> postDtoResponse = this.postService.getAllPostsByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoResponse);
    }

    @GetMapping({"/getPost/user/{userId}"})
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable("userId") Integer userId) {
        List<PostDto> postDtoResponse = this.postService.getAllPostsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoResponse);
    }

    @DeleteMapping({"/deletePost/{postId}"})
    public ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId) {
        String response = this.postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping({"/updatePost/{postId}"})
    public ResponseEntity<PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
        PostDto postDtoResponse = this.postService.updatePost(postDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoResponse);
    }

    @GetMapping("/searchPost/{keyword}")
    public ResponseEntity<List<PostDto>> searchPost (@PathVariable("keyword") String keyword) {
        List<PostDto> postDtos = this.postService.searchPost("%" + keyword + "%");
        return ResponseEntity.status(HttpStatus.OK).body(postDtos);
    }
}
