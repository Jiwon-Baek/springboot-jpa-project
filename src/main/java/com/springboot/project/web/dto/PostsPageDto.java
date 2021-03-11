package com.springboot.project.web.dto;


import lombok.Getter;



@Getter
public class PostsPageDto {

    //페이지를 위한 dto
    private int listNum;


    public PostsPageDto(int listNum){

        this.listNum=listNum;
    }

    
}
