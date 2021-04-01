package com.springboot.project.web.dto;



import com.springboot.project.doamin.message.Message;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MessageListDto {

    private final Long id;
    private final String title;
    private final String author;
    private final LocalDate createdDate;

    public MessageListDto(Message entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
    }
}
