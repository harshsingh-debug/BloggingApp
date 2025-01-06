package com.bloggingapp.service.implementations;

import com.bloggingapp.dto.PostDto;
import com.bloggingapp.entity.CategoryEntity;
import com.bloggingapp.entity.PostEntity;
import com.bloggingapp.entity.UserEntity;
import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.model.response.PostPaginationResponse;
import com.bloggingapp.repositories.CategoryRepo;
import com.bloggingapp.repositories.PostRepo;
import com.bloggingapp.repositories.UserRepo;
import com.bloggingapp.service.PostService;
import com.bloggingapp.utils.ObjectMapping;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ConfigurationException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ObjectMapping objectMapping;

    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo, ObjectMapping objectMapping) {
        this.postRepo = postRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
        this.objectMapping = objectMapping;
    }

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        if (postDto == null) {
            throw new IllegalArgumentException("Request body cannot be empty");
        } else if (userId == null) {
            throw new IllegalArgumentException("Usre id cannot be empty");
        } else if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be empty");
        } else {
            try {
                UserEntity userEntity = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(userId, "User does not exist"));
                CategoryEntity categoryEntity = this.categoryRepo.findById(categoryId).orElseThrow(() -> new DataNotFoundException(categoryId, "Category does not exist"));
                PostEntity postEntity = this.objectMapping.modelMapping(postDto, PostEntity.class);
                postEntity.setImageName("dummy");
                postEntity.setPostDate(new Date());
                postEntity.setUser(userEntity);
                postEntity.setCategory(categoryEntity);
                return this.objectMapping.modelMapping(this.postRepo.save(postEntity), PostDto.class);
            } catch (DataAccessException var7) {
                System.out.println(var7);
                throw new CustomServiceException("Unable to save data for id : 0");
            } catch (NullPointerException | IllegalArgumentException var8) {
                System.out.println(var8);
                throw new CustomServiceException("Some data is missing for id : 0");
            } catch (ConfigurationException var9) {
                System.out.println(var9);
                throw new CustomServiceException("Data could not be mapped for id : 0");
            }
        }
    }

    public PostDto updatePost(PostDto postDto, Integer postId) {
        if (postDto == null || postId < 1) {
            throw new IllegalArgumentException("Invalid post data provided");
        }
        try {
            PostEntity postEntity = postRepo.findById(postId).orElseThrow(() -> new DataNotFoundException(postId, "Data not found for id : " + postId));
            if (postDto.getPostTitle() != null) {
                postEntity.setPostTitle(postDto.getPostTitle());
            }
            if (postDto.getContent() != null) {
                postEntity.setPostTitle(postDto.getContent());
            }
            return objectMapping.modelMapping(postRepo.save(postEntity), PostDto.class);
        } catch (IllegalArgumentException var3) {
            System.out.println(var3);
            throw new CustomServiceException("Invalid data provided for id : " + postId);
        } catch (DataAccessException var4) {
            System.out.println(var4);
            throw new CustomServiceException("Cannot fetch data for id : " + postId);
        } catch (DataNotFoundException var5) {
            System.out.println(var5);
            throw new CustomServiceException("Data not present for id : " + postId);
        } catch (ConfigurationException var6) {
            System.out.println(var6);
            throw new CustomServiceException("Mapping failed for id : " + postId);
        }
    }

    public PostPaginationResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
        try {
            PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(new String[]{sortBy}));
            Page<PostEntity> pagePosts = this.postRepo.findAll(pageable);
            List<PostEntity> postEntities = pagePosts.getContent();
            if (postEntities != null && postEntities.size() != 0) {
                List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.objectMapping.modelMapping(postEntity, PostDto.class)).collect(Collectors.toList());
                postPaginationResponse.setPostContent(postDtos);
                postPaginationResponse.setPageNumber(pagePosts.getNumber());
                postPaginationResponse.setPageSize(pagePosts.getSize());
                postPaginationResponse.setTotalElements(pagePosts.getTotalElements());
                postPaginationResponse.setTotalPages(pagePosts.getTotalPages());
                postPaginationResponse.setIsLastPage(pagePosts.isLast());
                return postPaginationResponse;
            } else {
                return postPaginationResponse;
            }
        } catch (DataAccessException var9) {
            System.out.println(var9);
            throw new CustomServiceException("No data found in post table");
        } catch (ConfigurationException var10) {
            System.out.println(var10);
            throw new CustomServiceException("Data mapping failed");
        }
    }

    public PostDto getPostById(Integer postId) {
        if (postId != null && postId != 0) {
            try {
                PostEntity postEntity = this.postRepo.findById(postId).orElseThrow(() -> new DataNotFoundException(postId, "No post found for id : " + postId));
                return this.objectMapping.modelMapping(postEntity, PostDto.class);
            } catch (IllegalArgumentException var3) {
                System.out.println(var3);
                throw new CustomServiceException("Invalid data provided for id : " + postId);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Cannot fetch data for id : " + postId);
            } catch (DataNotFoundException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Data not present for id : " + postId);
            } catch (ConfigurationException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Mapping failed for id : " + postId);
            }
        } else {
            throw new IllegalArgumentException("Invalid post id");
        }
    }

    public String deletePost(Integer postId) {
        if (postId != null && postId != 0) {
            try {
                PostEntity postEntity = this.postRepo.findById(postId).orElseThrow(() -> new DataNotFoundException(postId, "No data found for id : " + postId));
                this.postRepo.delete(postEntity);
                return "Successfully deleted post for id :" + postId;
            } catch (IllegalArgumentException var3) {
                System.out.println(var3);
                throw new CustomServiceException("Invalid data provided for id : " + postId);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Cannot fetch data for id : " + postId);
            } catch (DataNotFoundException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Data not present for id : " + postId);
            }
        } else {
            throw new IllegalArgumentException("Invalid post id provided");
        }
    }

    public List<PostDto> getAllPostsByUser(Integer userId) {
        if (userId != null && userId != 0) {
            try {
                UserEntity userEntity = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(userId, "No data found for id : " + userId));
                List<PostEntity> postEntities = this.postRepo.findByUser(userEntity);
                if (postEntities != null && postEntities.size() != 0) {
                    List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.objectMapping.modelMapping(postEntity, PostDto.class)).collect(Collectors.toList());
                    return postDtos;
                } else {
                    return Collections.emptyList();
                }
            } catch (IllegalArgumentException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Invalid data provided for id : " + userId);
            } catch (DataAccessException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Cannot fetch data for id : " + userId);
            } catch (DataNotFoundException var7) {
                System.out.println(var7);
                throw new CustomServiceException("Data not present for id : " + userId);
            } catch (ConfigurationException var8) {
                System.out.println(var8);
                throw new CustomServiceException("Mapping failed for id : " + userId);
            }
        } else {
            throw new IllegalArgumentException("Invalid user id provided");
        }
    }

    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        if (categoryId != null && categoryId != 0) {
            try {
                CategoryEntity categoryEntity = this.categoryRepo.findById(categoryId).orElseThrow(() -> new DataNotFoundException(categoryId, "No data found for id : " + categoryId));
                List<PostEntity> postEntities = this.postRepo.findByCategory(categoryEntity);
                if (postEntities != null && postEntities.size() != 0) {
                    List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.objectMapping.modelMapping(postEntity, PostDto.class)).collect(Collectors.toList());
                    return postDtos;
                } else {
                    return Collections.emptyList();
                }
            } catch (IllegalArgumentException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Invalid data provided for id : " + categoryId);
            } catch (DataAccessException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Cannot fetch data for id : " + categoryId);
            } catch (DataNotFoundException var7) {
                System.out.println(var7);
                throw new CustomServiceException("Data not present for id : " + categoryId);
            } catch (ConfigurationException var8) {
                System.out.println(var8);
                throw new CustomServiceException("Mapping failed for id : " + categoryId);
            }
        } else {
            throw new IllegalArgumentException("Invalid post id provided");
        }
    }

    public List<PostDto> searchPost(String keyword) {
        if (keyword.isBlank()) {
            throw new IllegalArgumentException("Invalid data provided");
        }
        try {
            List<PostEntity> postEntities = postRepo.searchByKeyword(keyword);
            List<PostDto> postDtos = postEntities.stream().map((post) -> objectMapping.modelMapping(post, PostDto.class)).collect(Collectors.toList());
            return postDtos;
        } catch (IllegalArgumentException var5) {
            System.out.println(var5);
            throw new CustomServiceException("Invalid data provided for search string : " + keyword);
        } catch (DataAccessException var6) {
            System.out.println(var6);
            throw new CustomServiceException("Cannot fetch data for search string : " + keyword);
        } catch (ConfigurationException var8) {
            System.out.println(var8);
            throw new CustomServiceException("Mapping failed for search string : " + keyword);
        }
    }
}
