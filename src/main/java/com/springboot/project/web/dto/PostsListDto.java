package com.springboot.project.web.dto;

import com.springboot.project.doamin.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostsListDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}