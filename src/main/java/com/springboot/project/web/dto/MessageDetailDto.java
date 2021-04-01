package com.springboot.project.web.dto;

import com.springboot.project.doamin.message.Message;
import lombok.Builder;

import java.time.LocalDate;

public class MessageDetailDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate createdDate;


    @Builder
    public void MessageDetailDto (Message entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
    }
}
