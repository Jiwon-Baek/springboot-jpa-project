package com.springboot.project.service;


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


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    public Long signUp(UserSaveDto userSaveDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));

        return userRepository.save(userSaveDto.toEntity()).getId();
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        return new User(user.getUsername(), user.getPassword(), user.getRole());
    }


    //로그인시 비밀번호까지 일치한지 확인
    @Transactional
    public UserLoginDto findUserByUsername(String username, String password) {

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

        } else {
            throw new RuntimeException("없는 회원입니다.");
        }

        return userLoginDto;
    }

    //유저 확인 및 세션
    @Transactional
    public User userLoad(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        System.out.println(user.getUsername());

        return new User(user.getUsername(), user.getRole());
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

    @Transactional
    public Long findUsernameByNE(String name, String email) throws UsernameNotFoundException {

        User user = userRepository.findByNM(name, email);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        System.out.println(user.getUsername());

        return user.getId();
    }

    @Transactional
    public String findUsernameByid(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        return user.getUsername();
    }


    @Transactional
    public Long findPasswordByUNE(String username, String name, String email) throws UsernameNotFoundException {

        User user = userRepository.findByUNM(username, name, email);

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        return user.getId();
    }

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

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String temporarypw = passwordEncoder.encode(str);

            user.update(temporarypw);


        }


        return str;

    }


}