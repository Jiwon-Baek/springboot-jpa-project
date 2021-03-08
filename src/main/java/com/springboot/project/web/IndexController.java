package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;


import com.springboot.project.web.dto.PostsResponseDto;
import com.springboot.project.service.PostsService;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /*메인 페이지*/
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }

        return "index";
    }

    /*회원 가입*/
    @GetMapping("/signup")
    public String signup() {

        return "user/signup";
    }


    /*로그인*/
    @GetMapping("/login")
    public String login() {

        return "user/signin";
    }


    /*로그아웃*/
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }


    /*게시물 등록*/
    @GetMapping("/posts/save")
    public String postsSave(@LoginUser SessionUser user, Model model) {

        model.addAttribute("author", user.getUsername());

        return "posts/posts-save";
    }

    //게시물 세부 내용
    @GetMapping("/posts/detail/{id}")
    public String postsContent(@PathVariable Long id, Model model, @LoginUser SessionUser user) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("postD", dto);


        //로그인한 유저가 글을 작성한 유저와 다를 때 수정,삭제 버튼이 안뜨게 하기 위함
        if (user != null) {
            if (dto.getAuthor().equals(user.getUsername())) {
                model.addAttribute("postUser", user.getUsername());
            }
        }

        return "posts/posts-detail";
    }


    /*게시물 수정*/
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts/posts-update";
    }


}