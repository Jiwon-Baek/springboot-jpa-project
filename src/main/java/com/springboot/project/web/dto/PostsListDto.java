package com.springboot.project.web.dto;


import com.springboot.project.doamin.posts.Posts;

import lombok.Getter;


import java.time.LocalDate;


@Getter
public class PostsListDto {

    private final Long id;
    private final String title;
    private final String author;
    private final int hit;
    private final LocalDate createdDate;


    public PostsListDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.hit = entity.getHit();
        this.createdDate = entity.getCreatedDate();
    }

}