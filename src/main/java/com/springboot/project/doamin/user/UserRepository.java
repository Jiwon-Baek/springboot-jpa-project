package com.springboot.project.doamin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "from User where username=:username")
    User findByUsername(@Param("username") String username);

    @Query(value = "from User where email=:email")
    String findByEmail(@Param("email") String email);


    @Query(value = "select username from User where name =:name and email=:email")
    String findId(@Param("name") String name,@Param("email") String email );

}
