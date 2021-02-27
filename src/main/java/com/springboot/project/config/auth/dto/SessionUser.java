package com.springboot.project.config.auth.dto;


import com.springboot.project.doamin.user.User;
import lombok.Getter;

import java.io.Serializable;

//인증된 사용자 정보만 필요하다
@Getter
public class SessionUser implements Serializable {

    private String username;
    private String name;
    private String email;


    public SessionUser(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();

    }
}
