package com.springboot.project.web.dto;


import com.springboot.project.doamin.comments.Comments;
import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentsListDto {

    private final Long id;
    private final String author;
    private final String content;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;
    private final User user;
    private final Posts posts;

    public CommentsListDto(Comments entity) {

        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.createdDate = getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.posts = entity.getPosts();
    }
}
