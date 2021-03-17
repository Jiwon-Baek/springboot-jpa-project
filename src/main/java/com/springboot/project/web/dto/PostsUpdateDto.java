package com.springboot.project.web.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostsUpdateDto {

    private String title;
    private String content;

    @Builder
    public PostsUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
