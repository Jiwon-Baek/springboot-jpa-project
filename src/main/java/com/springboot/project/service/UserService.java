package com.springboot.project.service;

import com.springboot.project.config.auth.dto.SessionUser;

import com.springboot.project.doamin.user.User;
import com.springboot.project.doamin.user.UserRepository;

import com.springboot.project.web.dto.*;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;



@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;



    //회원가입
    @Transactional
    public Long signUp(UserSaveDto userSaveDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));

        return userRepository.save(userSaveDto.toEntity()).getId();
    }


    //유저 아이디로 유저가 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User entity = userRepository.findByUsername(username);

        if(entity == null) throw new UsernameNotFoundException("해당 아이디를 가진 유저를 찾을 수 없습니다");

        return entity;

    }


    //로그인시 비밀번호까지 일치한지 확인
    @Transactional
    public UserLoginDto findUserByUP(String username, String password) {

        User user = this.userRepository.findByUsername(username);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (user == null) {
            throw new RuntimeException("user not found");
        }
        UserLoginDto userLoginDto = new UserLoginDto();


        String rawPassword = password;//입력된 비밀번호
        String encodedPassword = user.getPassword();//암호화되어 DB에 저장된 패스워드

        //회원정보가 있을 때 api로 넘김
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            System.out.println("계정정보 일치");

            userLoginDto.setUsername(user.getUsername());
            userLoginDto.setPassword(user.getPassword());

            httpSession.setAttribute("user", new SessionUser(user));//세션 유지


        } else {
            throw new RuntimeException("없는 회원입니다.");
        }

        return userLoginDto;
    }


    //아이디 중복체크
    @Transactional
    public String usernameCheck(String username) {

        System.out.print(username);

        if (userRepository.findByUsername(username) == null) {
            return "YES";
        } else {
            return "NO";
        }
    }

    //이름과 이메일로 아이디 찾기
    @Transactional
    public Long findUsernameByNE(String name, String email) throws UsernameNotFoundException {

        User user = userRepository.findByNM(name, email);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        System.out.println(user.getUsername());

        return user.getId();
    }

    //user id(회원번호)로 유저 아이디 찾기
    @Transactional
    public String findUsernameByid(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        return user.getUsername();
    }


    //아이디,이름,이메일로 유저찾기
    @Transactional
    public Long findPasswordByUNE(String username, String name, String email) throws UsernameNotFoundException {

        User user = userRepository.findByUNM(username, name, email);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        return user.getId();
    }

    //user id로 비밀번호 찾아 임시번호 발급
    @Transactional
    public String findPasswordByid(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));


        String str = "";

        //임시 비밀번호 구현
        if (user != null) {

            char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                    'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


            int idx = 0;

            for (int i = 0; i < 10; i++) {
                idx = (int) (charSet.length * Math.random());
                str += charSet[idx];
            }

            //임시번호를 유저 데이터로 업뎃
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String temporarypw = passwordEncoder.encode(str);

            user.updatePw(temporarypw);

        }


        return str;

    }

    //유저 아이디로 유저 정보 찾기
    @Transactional
    public UserResponseDto findUserByUsername(String username) {

        User userEntity = userRepository.findByUsername(username);

        return new UserResponseDto(userEntity);

    }


    //회원 정보 수정
    @Transactional
    public String update(String username, UserUpdateDto requestDto) throws Exception {


        User user = userRepository.findByUsername(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(requestDto.getPassword());

        userRepository.updateUser(username, password, requestDto.getName(), requestDto.getEmail());

        return user.getUsername();
    }

    //회원 삭제를 위한 서비스
    @Transactional
    public Long findIdByPassword(String username, String password) {

        User user = this.userRepository.findByUsername(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = password;
        String encodedPassword = user.getPassword();

        if (passwordEncoder.matches(rawPassword, encodedPassword)) {

            return user.getId();

        } else {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
    }

    //회원 삭제
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        httpSession.invalidate();//세션 저장된 내용 삭제

        userRepository.delete(user);
    }


}