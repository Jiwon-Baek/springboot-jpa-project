package com.springboot.project.web.dto;


import lombok.Getter;


@Getter
public class PageDto {

    //페이지를 위한 dto
    private int listNum;


    public PageDto(int listNum) {

        this.listNum = listNum;
    }


}
