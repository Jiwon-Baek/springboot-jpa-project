package com.springboot.project.web;


import com.springboot.project.service.UserService;
import com.springboot.project.web.dto.UserLoginDto;
import com.springboot.project.web.dto.UserResponseDto;
import com.springboot.project.web.dto.UserSaveDto;
import com.springboot.project.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;




@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


   //회원 가입
    @PostMapping("/api/v1/userLogin/signup")
    public Long signUp(@RequestBody UserSaveDto userSaveDto) {

        return userService.signUp(userSaveDto);
    }

    //아이디 중복 체크
    @PostMapping("/api/v1/userLogin/idCheck")
    public String idCheck(String username) {
        String str = userService.usernameCheck(username);
        return str;
    }

    //로그인
    @PostMapping("api/v1/userLogin/login")
    public UserLoginDto login(@RequestBody UserLoginDto dto) {

        System.out.println("Login attmpted :: " + dto.getUsername());
        System.out.println("Login attmpted :: " + dto.getPassword());

        UserLoginDto userLoginDto = userService.findUserByUP(dto.getUsername(),dto.getPassword());

        return userLoginDto;

    }


    @PostMapping("api/v1/userLogin/findid")
    public Long findIdByNE(@RequestBody UserResponseDto user) {

       Long id = userService.findUsernameByNE(user.getName(),user.getEmail());

        return id;

    }


    @PostMapping("api/v1/userLogin/findpassword")
    public Long findPasswordByUNE(@RequestBody UserResponseDto user) {

        Long id = userService.findPasswordByUNE(user.getUsername(),user.getName(),user.getEmail());

        return id;

    }

    @PutMapping("/api/v1/user/update/{username}")
    public String update(@PathVariable String username,@RequestBody UserUpdateDto requestDto) throws Exception {

        return userService.update(username, requestDto);
    }

    @PostMapping("api/v1/user/withdrawal/{username}")
    public Long withdrawal(@PathVariable String username, @RequestBody UserLoginDto dto) {

        Long id = userService.findIdByPassword(username, dto.getPassword());

        return id;

    }

    @DeleteMapping("/api/v1/user/withdrawal/{id}")
    public Long delete(@PathVariable Long id) {
        System.out.println(id);
        userService.delete(id);
        return id;
    }
}
