package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.service.PostsService;
import com.springboot.project.service.UserService;
import com.springboot.project.web.dto.PostsResponseDto;
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
    private final PostsService postsService;

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
    @GetMapping("/mypage/detail")
    public String mypageDetail(@LoginUser SessionUser user, Model model) {

        UserResponseDto dto = userService.findUserByUsername(user.getUsername());
        model.addAttribute("userD", dto);

        return "/user/mypage-detail";
    }

    //유저 개인 정보 수정 페이지
    @GetMapping("/mypage/update")
    public String mypageUpdate(@LoginUser SessionUser user, Model model) {

        UserResponseDto dto = userService.findUserByUsername(user.getUsername());
        model.addAttribute("users", dto);

        return "/user/mypage-update";
    }

    //회원 탈퇴 페이지
    @GetMapping("/mypage/withdrawal")
    public String userWithdrawal(@LoginUser SessionUser user,Model model) {

        model.addAttribute("username",user.getUsername());

        return "user/mypage-withdrawal";
    }

    //해당 회원이 쓴 게시물 조회
    @GetMapping("/mypage/posts")
    public String userPosts(@LoginUser SessionUser user, Model model) {

        //유저의 회원 번호를 받아
        UserResponseDto userDto = userService.findUserByUsername(user.getUsername());

        Long id =userDto.getId();
        System.out.println(id);

        //해당 회원번호로 저장된 게시물을 전부  출력
        model.addAttribute("userPosts",postsService.findByUserId(id));


        return "user/mypage-myposts";
    }



}
