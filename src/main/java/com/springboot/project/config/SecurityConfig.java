package com.springboot.project.config;


import com.springboot.project.doamin.user.Role;
import com.springboot.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final UserService userService;
        // 암호화 방식 빈(Bean) 생성
        @Bean
        public PasswordEncoder passwordEncoder() {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .headers().frameOptions().disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true); // 세션 날리기

        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
            }


        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return super.userDetailsService();
        }

        @Bean
        public DaoAuthenticationProvider getDaoAuthenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            //call the userDetailsService() method here
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(this.passwordEncoder());
            return authProvider;
        }
}
