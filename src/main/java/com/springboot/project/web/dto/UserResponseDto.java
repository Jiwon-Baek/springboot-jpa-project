package com.springboot.project.web.dto;



import com.springboot.project.doamin.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String password;
    private String name;
    private String email;

    public UserResponseDto(User entity) {

        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.name = entity.getName();

    }
}
