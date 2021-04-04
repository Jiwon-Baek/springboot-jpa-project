package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.service.MessageService;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class MessagePageController {

    private final MessageService messageService;

    /*메세지(쪽지) 전체 출력*/
    @GetMapping("/mypage/message/list")
    public String messageList(Model model, @LoginUser SessionUser user, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {


        //메세지(쪽지) 목록
        model.addAttribute("messages", messageService.findMessageByPageRequest(user.getUsername(),pageable));

        //페이지 리스트
        model.addAttribute("messagePageList", messageService.getPageList(pageable));


        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }

        return "/mustache/messages/message-list";
    }

    //메세지(쪽지) 쓰기
    @GetMapping("/mypage/message/post")
    public String messageSave(@LoginUser SessionUser user, Model model) {

        model.addAttribute("messageAuthor", user.getUsername());

        return "/mustache/messages/message-post";
    }

    //메세지(쪽지) 세부 내용
    @GetMapping("/mypage/message/detail/{id}")
    public String messageDetail(@LoginUser SessionUser user, Model model,@PathVariable Long id) {

        model.addAttribute("messageD", messageService.findById(id));


        return "/mustache/messages/message-detail";
    }


}
