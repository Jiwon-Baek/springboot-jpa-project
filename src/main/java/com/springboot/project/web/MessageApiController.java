package com.springboot.project.web;


import com.springboot.project.service.MessageService;

import com.springboot.project.web.dto.MessageSaveDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class MessageApiController {

    private final MessageService messageService;

    @PostMapping("/api/v1/message/post")
    public Long save(@RequestBody MessageSaveDto messageSaveDto) {

        System.out.println(messageSaveDto.getRecipients());

        return messageService.save(messageSaveDto);
    }



}
