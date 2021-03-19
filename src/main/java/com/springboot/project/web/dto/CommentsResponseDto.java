package com.springboot.project.web.dto;


import com.springboot.project.doamin.comments.Comments;
import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentsResponseDto {

    private Long id;
    private String author;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private User user;
    private Posts posts;


    public CommentsResponseDto(Comments entity) {

        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.createdDate = getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.posts = entity.getPosts();
    }

}
