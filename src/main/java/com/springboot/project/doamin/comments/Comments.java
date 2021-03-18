package com.springboot.project.doamin.comments;

import com.springboot.project.doamin.BaseTimeEntity;
import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comments(Long id, String author, String content, Posts post_id, User user_id) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.posts = post_id;
        this.user = user_id;
    }

    public void update(String content) {
        this.content = content;
    }
}
