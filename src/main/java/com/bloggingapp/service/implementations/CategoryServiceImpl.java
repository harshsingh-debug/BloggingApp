package com.bloggingapp.service.implementations;

import com.bloggingapp.dto.CategoryDto;
import com.bloggingapp.entity.CategoryEntity;
import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.repositories.CategoryRepo;
import com.bloggingapp.service.CategoryService;
import com.bloggingapp.utils.ObjectMapping;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ConfigurationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ObjectMapping objectMapping;
    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(ObjectMapping objectMapping, CategoryRepo categoryRepo) {
        this.objectMapping = objectMapping;
        this.categoryRepo = categoryRepo;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Integer categoryId = 0;
        if (categoryDto == null) {
            throw new IllegalArgumentException("Category cannot be empty");
        } else {
            try {
                CategoryEntity categoryEntity = this.objectMapping.modelMapping(categoryDto, CategoryEntity.class);
                categoryId = categoryEntity.getCategoryId();
                return this.objectMapping.modelMapping(this.categoryRepo.save(categoryEntity), CategoryDto.class);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Unable to save data for id : " + categoryId);
            } catch (NullPointerException | IllegalArgumentException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Some data is missing for id : " + categoryId);
            } catch (ConfigurationException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Data could not be mapped for id : " + categoryId);
            }
        }
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        if (categoryDto == null) {
            throw new IllegalArgumentException("Empty category data");
        } else {
            try {
                CategoryEntity categoryEntity = this.categoryRepo.findById(categoryId).orElseThrow(() -> new DataNotFoundException(categoryId, "Category does not exist"));
                if (categoryDto.getCategoryTitle() != null) {
                    categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
                }

                if (categoryDto.getCategoryDescription() != null) {
                    categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
                }

                return this.objectMapping.modelMapping(this.categoryRepo.save(categoryEntity), CategoryDto.class);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Could not fetch data for id : " + categoryId);
            } catch (DataNotFoundException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Category does not exist for id : " + categoryId);
            } catch (ConfigurationException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Data could not be mapped for id : " + categoryId);
            }
        }
    }

    public CategoryDto getCategoryById(Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be empty");
        } else {
            try {
                CategoryEntity categoryEntity = this.categoryRepo.findById(categoryId).orElseThrow(() -> new DataNotFoundException(categoryId, "Category does not exist"));
                return this.objectMapping.modelMapping(categoryEntity, CategoryDto.class);
            } catch (DataNotFoundException var3) {
                System.out.println(var3);
                throw new CustomServiceException("Category does now exist for id : " + categoryId);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Could not fetch data for id : " + categoryId);
            } catch (ConfigurationException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Data could not be mapped for id : " + categoryId);
            } catch (Exception var6) {
                System.out.println(var6);
                throw new CustomServiceException("Some exception occured for id : " + categoryId);
            }
        }
    }

    public List<CategoryDto> getAllCategory() {
        try {
            List<CategoryEntity> categoryEntities = this.categoryRepo.findAll();
            if (categoryEntities != null && categoryEntities.size() != 0) {
                List<CategoryDto> categoryDtoList = categoryEntities.stream().map((categoryEntity) -> this.objectMapping.modelMapping(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
                return categoryDtoList;
            } else {
                return Collections.emptyList();
            }
        } catch (DataAccessException var3) {
            System.out.println(var3);
            throw new CustomServiceException("Could not fetch data for category");
        } catch (ConfigurationException var4) {
            System.out.println(var4);
            throw new CustomServiceException("Data could not be mapped");
        }
    }

    public String deleteCategory(Integer categoryId) {
        try {
            CategoryEntity categoryEntity = this.categoryRepo.findById(categoryId).orElseThrow(() -> new DataNotFoundException(categoryId, "Category does not exist"));
            this.categoryRepo.delete(categoryEntity);
            return "Successfully deleted the category with id : " + categoryId;
        } catch (DataNotFoundException var3) {
            System.out.println(var3);
            throw new CustomServiceException("Category not found for id : " + categoryId);
        } catch (DataAccessException var4) {
            System.out.println(var4);
            throw new CustomServiceException("Could not fetch data for id : " + categoryId);
        } catch (ConfigurationException var5) {
            System.out.println(var5);
            throw new CustomServiceException("Data could not be mapped for id : " + categoryId);
        }
    }
}


