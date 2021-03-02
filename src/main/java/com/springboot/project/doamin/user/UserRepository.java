package com.springboot.project.doamin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "from User where username=:username")
    User findByUsername(@Param("username") String username);


    @Query(value = "select username from User where id=:id")
    String findIdd(@Param("id") Long id);


    @Query(value = "select id from User where name =:name and email=:email")
    Long findId(@Param("name") String name,@Param("email") String email );



}
