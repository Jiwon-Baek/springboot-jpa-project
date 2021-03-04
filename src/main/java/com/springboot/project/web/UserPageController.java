package com.springboot.project.web;


import com.springboot.project.service.UserService;
import com.springboot.project.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class UserPageController {

    /*유저 페이지*/

    private final UserService userService;

    //아이디 찾기
    @GetMapping("/findid")
    public String findId() {

        return "user/findId";
    }

    @GetMapping("/findid/{id}")
    public String findId2(@PathVariable Long id, Model model) {

        String username = userService.findUsernameByid(id);
        model.addAttribute("findid", username);

        return "user/findId2";
    }

    //비밀번호 찾기
    @GetMapping("/findpassword")
    public String findPassword() {

        return "user/findpassword";
    }

    @GetMapping("/findpassword/{id}")
    public String findPassword(@PathVariable Long id, Model model) {

        String password = userService.findPasswordByid(id);
        model.addAttribute("findpassword", password);

        return "user/findpassword2";
    }

    //유저 개인 정보 페이지
    @GetMapping("/mypage/detail/{username}")
    public String mypageDetail(@PathVariable String username, Model model) {

        UserResponseDto dto = userService.findUserByUsername(username);
        model.addAttribute("userD", dto);

        return "/user/mypage-detail";
    }

    //유저 개인 정보 수정 페이지
    @GetMapping("/mypage/update/{username}")
    public String mypageUpdate(@PathVariable String username, Model model) {

        UserResponseDto dto = userService.findUserByUsername(username);
        model.addAttribute("users", dto);

        return "/user/mypage-update";
    }


}
