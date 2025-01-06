package com.bloggingapp.controller;

import com.bloggingapp.dto.CommentDto;
import com.bloggingapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping ("/createComment/{postId}")
    public ResponseEntity<CommentDto> createComment (@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId) {
        CommentDto commentDtoResponse = this.commentService.createComment(commentDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtoResponse);
    }

    @DeleteMapping ("/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment (@PathVariable("commentId") Integer commentId) {
        String response = this.commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
