package com.springboot.project.web.dto;


import com.springboot.project.doamin.posts.Posts;
import lombok.*;


import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private int hit;
    private LocalDate createdDate;
    private LocalDate modifiedDate;


    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.hit = entity.getHit();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}