package com.springboot.project.web;





import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.doamin.user.User;
import com.springboot.project.service.UserService;
import com.springboot.project.web.dto.UserLoginDto;
import com.springboot.project.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;



    @PostMapping("/api/v1/user")
    public Long signUp(@RequestBody UserSaveDto userSaveDto) {

        return userService.signUp(userSaveDto);
    }


    @PostMapping("api/v1/login")
    public UserLoginDto login(@RequestBody UserLoginDto user,HttpSession session) {

        System.out.println("Login attmpted :: " + user.getUsername());
        System.out.println("Login attmpted :: " + user.getPassword());


        UserLoginDto userLoginDto = userService.findUserByUsername(user.getUsername(),user.getPassword());



        //session.setAttribute("username1", user.getUsername());
        User user1 = userService.userLoad(user.getUsername()); //받아온 유저 정보 저장

        session.setAttribute("user", new SessionUser(user1)); //세션 저장


        return userLoginDto;

    }

}
