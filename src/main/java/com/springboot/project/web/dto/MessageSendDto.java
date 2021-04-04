package com.springboot.project.web.dto;



import com.springboot.project.doamin.message.Message;
import com.springboot.project.doamin.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageSendDto {

    private String title;
    private String content;
    private String author;
    private String recipients;
    private User user;

    @Builder
    public MessageSendDto(String title, String content, String recipients, String author) {
        this.title = title;
        this.content = content;
        this.recipients = recipients;
        this.author = author;

    }

    public Message toEntity() {
        return Message.builder()
                .title(title)
                .content(content)
                .author(author)
                .recipients(recipients)
                .readCheck(false)
                .user_id(user)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
