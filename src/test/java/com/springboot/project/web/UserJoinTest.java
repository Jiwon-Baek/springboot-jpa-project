package com.springboot.project.web;


import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserJoinTest {

    @Test
    public void save() {

        // 회원 저장
        User user1 = new User(1L, "김건강");


        // 게시물1 저장
        Posts posts1 = new Posts(1L, "김건강");
        posts1.setUser(user1);  // 연관관계 설정


        // 게시물2 저장
        Posts posts2 = new Posts(2L, "김선생");
        posts2.setUser(user1);  // 연관관계 설정

        user1.getPosts().add(posts1);
        user1.getPosts().add(posts2);

        List<Posts> posts = user1.getPosts();
        System.out.println("members.size = " + posts.size());

        }
}
