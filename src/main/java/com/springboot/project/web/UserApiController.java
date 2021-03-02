package com.springboot.project.web;





import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.doamin.user.User;
import com.springboot.project.service.UserService;
import com.springboot.project.web.dto.UserLoginDto;
import com.springboot.project.web.dto.UserResponseDto;
import com.springboot.project.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


   //회원 가입
    @PostMapping("/api/v1/user")
    public Long signUp(@RequestBody UserSaveDto userSaveDto) {

        return userService.signUp(userSaveDto);
    }

    //아이디 중복 체크
    @PostMapping("/api/v1/idCheck")
    public String idCheck(String username) {
        String str = userService.usernameCheck(username);
        return str;
    }

    //로그인
    @PostMapping("api/v1/login")
    public UserLoginDto login(@RequestBody UserLoginDto user,HttpSession session) {

        System.out.println("Login attmpted :: " + user.getUsername());
        System.out.println("Login attmpted :: " + user.getPassword());


        UserLoginDto userLoginDto = userService.findUserByUsername(user.getUsername(),user.getPassword());

        User user1 = userService.userLoad(user.getUsername()); //받아온 유저 정보 저장

        session.setAttribute("username", new SessionUser(user1)); //세션 저장


        return userLoginDto;

    }

    @PostMapping("api/v1/findid")
    public Long findIdByNE(@RequestBody UserResponseDto user) {

       Long id = userService.findUsernameByNE(user.getName(),user.getEmail());

        return id;

    }

}
