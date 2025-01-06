package com.bloggingapp.controller;

import com.bloggingapp.dto.CategoryDto;
import com.bloggingapp.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/category"})
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping({"/createCategory"})
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto categoryDtoResponse = this.categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoResponse);
    }

    @PutMapping({"/updateCategory/{id}"})
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Integer categoryId) {
        CategoryDto categoryDtoResponse = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoResponse);
    }

    @GetMapping({"/getCategory/{id}"})
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Integer categoryId) {
        CategoryDto categoryDtoResponse = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoResponse);
    }

    @GetMapping({"/getCategory"})
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> categoryDtoResponse = this.categoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoResponse);
    }

    @DeleteMapping({"/deleteCategory/{id}"})
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer categoryId) {
        String response = this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
