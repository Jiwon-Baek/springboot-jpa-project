package com.springboot.project.web;

import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.service.CommentsService;

import com.springboot.project.web.dto.CommentsSaveDto;
import com.springboot.project.web.dto.CommentsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentsService commentsService;

    //댓글 저장
    @PostMapping("/api/v1/comments/{post_id}")
    public Long save(@RequestBody CommentsSaveDto saveDto, @LoginUser SessionUser user, @PathVariable Long post_id) {

        String username = user.getUsername();

        return commentsService.save(saveDto,post_id, username);
    }

    //댓글 수정
    @PutMapping("/api/v1/comments/{id}")
    public Long update(@PathVariable Long id, @RequestBody CommentsUpdateDto requestDto) {
        return commentsService.update(id, requestDto);
    }

    //댓글 삭제
    @DeleteMapping("/api/v1/comments/{id}")
    public Long delete(@PathVariable Long id) {
        commentsService.delete(id);
        return id;
    }


}
