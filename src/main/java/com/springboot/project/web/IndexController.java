package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.doamin.user.User;
import com.springboot.project.service.UserService;
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
    private final UserService userService;

    /*메인 페이지*/
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("user", user.getUsername());
            System.out.print("아이디" + user.getUsername());
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
    public String postsSave() {

        return "posts/posts-save";
    }

    //게시물 세부 내용
    @GetMapping("/posts/detail/{id}")
    public String postsContent(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("postD", dto);

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