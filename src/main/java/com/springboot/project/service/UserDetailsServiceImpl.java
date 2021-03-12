package com.springboot.project.service;
import com.springboot.project.config.auth.dto.SessionUser;
import com.springboot.project.doamin.user.MyUserDetails;
import com.springboot.project.doamin.user.User;
import com.springboot.project.doamin.user.UserDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;




public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserDetailsRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        System.out.println("여기"+username);


        return new MyUserDetails(user);
    }

}
