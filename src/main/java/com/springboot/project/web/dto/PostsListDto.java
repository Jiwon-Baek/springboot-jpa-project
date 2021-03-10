package com.springboot.project.web.dto;

import com.springboot.project.doamin.posts.Posts;

import com.springboot.project.doamin.user.User;
import javafx.scene.NodeBuilder;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;



@Getter
public class PostsListDto {

    private final Long id;
    private final String title;
    private final String author;
    private final LocalDateTime createdDate;



    public PostsListDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.createdDate=entity.getCreatedDate();
    }

}