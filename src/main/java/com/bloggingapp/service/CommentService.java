package com.bloggingapp.service;

import com.bloggingapp.dto.CommentDto;

public interface CommentService {
    CommentDto createComment (CommentDto commentDto, Integer postId);
    String deleteComment (Integer commentID);
}
