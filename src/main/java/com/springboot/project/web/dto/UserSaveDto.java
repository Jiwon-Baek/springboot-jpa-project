package com.springboot.project.web.dto;


import com.springboot.project.doamin.user.Role;
import com.springboot.project.doamin.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveDto {


    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    @Builder
    public UserSaveDto(String username,String password, String name, String email, String role){

        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .role(Role.USER.getKey())
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
