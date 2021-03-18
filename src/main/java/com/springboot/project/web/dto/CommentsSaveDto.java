package com.springboot.project.web.dto;


import com.springboot.project.doamin.comments.Comments;
import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentsSaveDto {

    private String author;
    private String content;
    private Posts posts;
    private User user;

    @Builder
    public CommentsSaveDto(String content) {
        this.content = content;
    }

    public Comments toEntity() {
        return Comments.builder()
                .author(author)
                .content(content)
                .post_id(posts)
                .user_id(user)
                .build();
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
