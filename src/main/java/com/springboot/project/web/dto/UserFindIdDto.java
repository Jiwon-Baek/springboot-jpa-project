package com.springboot.project.web.dto;



import com.springboot.project.doamin.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindIdDto {

    private String name;
    private String email;

    public UserFindIdDto(User entity) {
     this.name = entity.getName();
     this.email = entity.getEmail();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
