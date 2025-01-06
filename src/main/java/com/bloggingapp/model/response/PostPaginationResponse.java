package com.bloggingapp.model.response;

import com.bloggingapp.dto.PostDto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPaginationResponse {
    private List<PostDto> postContent;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
}
