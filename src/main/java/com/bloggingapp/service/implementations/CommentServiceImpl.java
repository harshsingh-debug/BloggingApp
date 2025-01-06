package com.bloggingapp.service.implementations;

import com.bloggingapp.dto.CommentDto;
import com.bloggingapp.entity.CommentEntity;
import com.bloggingapp.entity.PostEntity;
import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.repositories.CommentRepo;
import com.bloggingapp.repositories.PostRepo;
import com.bloggingapp.service.CommentService;
import com.bloggingapp.utils.ObjectMapping;
import org.modelmapper.ConfigurationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepo postRepo;

    private CommentRepo commentRepo;

    private ObjectMapping objectMapping;

    public CommentServiceImpl (PostRepo postRepo, CommentRepo commentRepo, ObjectMapping objectMapping) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.objectMapping = objectMapping;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        if (commentDto == null || postId < 1) {
            throw new IllegalArgumentException("Invalid argument data provided");
        }
        try {
            PostEntity postEntity = this.postRepo.findById(postId).orElseThrow(() -> new DataNotFoundException(postId, "Data not found for id : " + postId));
            CommentEntity commentEntity = objectMapping.modelMapping(commentDto, CommentEntity.class);
            commentEntity.setPostEntity(postEntity);
            postEntity.getComments().add(commentEntity);
            this.postRepo.save(postEntity);
            return objectMapping.modelMapping(commentRepo.save(commentEntity), CommentDto.class);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw new CustomServiceException("Invalid argument data provided");
        } catch (DataNotFoundException e) {
            System.out.println(e);
            throw new CustomServiceException("Data not found for the post with id : " + postId);
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CustomServiceException("No data found for post id : " + postId);
        } catch (ConfigurationException e) {
            System.out.println(e);
            throw new CustomServiceException("Mapping for data failed");
        }
    }

    @Override
    public String deleteComment(Integer commentID) {
        if (commentID < 1) {
            throw new IllegalArgumentException("Invalid data provided");
        }
        try {
            CommentEntity commentEntity = this.commentRepo.findById(commentID).orElseThrow(() -> new DataNotFoundException(commentID, "No data found for id : " + commentID));
            this.commentRepo.delete(commentEntity);
            return "Successfully deleted the comment";
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw new CustomServiceException("Invalid argument data provided");
        } catch (DataNotFoundException e) {
            System.out.println(e);
            throw new CustomServiceException("Data not found for the comment with id : " + commentID);
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CustomServiceException("No data found for comment id : " + commentID);
        }
    }
}
