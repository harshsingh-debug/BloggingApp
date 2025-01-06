package com.bloggingapp.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    @NotEmpty
    private String postTitle;
    @NotEmpty
    private String content;
    private String imageName;
    private Date postDate;
    private UserDto user;
    private CategoryDto category;
    private List<CommentDto> comments = new ArrayList<>();
}

