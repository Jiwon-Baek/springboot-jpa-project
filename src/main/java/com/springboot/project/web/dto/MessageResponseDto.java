package com.springboot.project.web.dto;


import com.springboot.project.doamin.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private boolean isReadCheck;

    public MessageResponseDto(Message entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.isReadCheck = entity.isReadCheck();
    }

}
