package com.springboot.project.web.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentsUpdateDto {

    private String content;

    @Builder
    public CommentsUpdateDto(String content){
        this.content = content;
    }
}
