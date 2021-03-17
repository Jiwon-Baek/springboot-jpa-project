package com.springboot.project.web.dto;

import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveDto {

    private String title;
    private String content;
    private String author;
    private int hit;
    private User user;

    @Builder
    public PostsSaveDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;

    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .hit(0)
                .user_id(user)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
