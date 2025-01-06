package com.bloggingapp.entity;

import jakarta.persistence.*;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer postId;
    @Column(name = "title")
    private String postTitle;
    @Column(length = 1000)
    private String content;
    private String imageName;
    private Date postDate;
    @ManyToOne
    private CategoryEntity category;
    @ManyToOne
    private UserEntity user;

    @OneToMany (cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();
}
