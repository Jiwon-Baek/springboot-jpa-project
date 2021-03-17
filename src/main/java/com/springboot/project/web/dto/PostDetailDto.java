package com.springboot.project.web.dto;

import com.springboot.project.doamin.posts.Posts;
import lombok.Builder;

import java.time.LocalDate;


public class PostDetailDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate modifiedDate;


    @Builder
    public void PostsDetailDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
