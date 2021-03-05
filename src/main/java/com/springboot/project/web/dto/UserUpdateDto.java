package com.springboot.project.web.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserUpdateDto {



    private String password;
    private String name;
    private String email;


    @Builder
    public UserUpdateDto( String password, String name, String email){


        this.password = password;
        this.name = name;
        this.email = email;

    }

}
