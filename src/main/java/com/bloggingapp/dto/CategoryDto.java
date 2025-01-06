package com.bloggingapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty ( message = "Title cannot be empty" )
    private String categoryTitle;
    @NotEmpty ( message = "Description cannot be empty" )
    private String categoryDescription;
}

