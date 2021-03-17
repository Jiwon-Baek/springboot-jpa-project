package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;


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


    /*메인 페이지*/

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        //로그인이 되어있을 때
        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }


        return "/mustache/index";
    }


    /*회원 가입*/
    @GetMapping("/signup")
    public String signup() {

        return "/mustache/user/signup";
    }


    /*로그인*/
    @GetMapping("/login")
    public String login() {

        return "/mustache/user/signin";
    }


    /*로그아웃*/
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }


}