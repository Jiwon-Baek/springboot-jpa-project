package com.springboot.project.web;


import com.springboot.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserService userService;


}
