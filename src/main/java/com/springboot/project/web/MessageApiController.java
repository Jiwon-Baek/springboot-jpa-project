package com.springboot.project.web;


import com.springboot.project.service.MessageService;

import com.springboot.project.web.dto.MessageSendDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@RestController
public class MessageApiController {

    private final MessageService messageService;

    //메세지(쪽지) 보내기
    @PostMapping("/api/v1/message/post")
    public Long messageSend(@RequestBody MessageSendDto messageSendDto) {

        System.out.println(messageSendDto.getRecipients());

        return messageService.save(messageSendDto);
    }

    //메세지(쪽지) 삭제
    @DeleteMapping("/api/v1/message/delete/{id}")
    public Long delete(@PathVariable Long id) {

        messageService.delete(id);

        return id;
    }

    @PostMapping("/api/v1/message/deleteArr")
    public int deleteArr(@RequestParam(value = "chbox[]") List<String> chArr) {


        int result = 0;
        Long cartNum = 0L;

        for(String i : chArr) {
            cartNum = Long.valueOf(i);
            messageService.delete(cartNum);
        }

        result = 1;

        return result;
    }


}
