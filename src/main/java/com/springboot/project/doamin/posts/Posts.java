package com.springboot.project.doamin.posts;


import com.springboot.project.doamin.BaseTimeEntity;

import com.springboot.project.doamin.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private int hit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Posts(Long id, String title, String content, String author, int hit, User user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.hit = hit;
        this.user = user_id;
    }

    public Posts(long id, String author) {
        this.id = id;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    //연관관계 설정
    public void setUser(User user) {

        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }


}