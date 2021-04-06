package com.springboot.project.doamin.message;


import com.springboot.project.doamin.BaseTimeEntity;
import com.springboot.project.doamin.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "message")
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    private String recipients;

    private boolean readCheck;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Message> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Message(Long id, String title, String content, String author,String recipients,boolean readCheck, User user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.recipients = recipients;
        this.readCheck=readCheck;
        this.user = user_id;
    }

    public void setReadCheck(boolean readCheck) {
        this.readCheck = readCheck;
    }

}
