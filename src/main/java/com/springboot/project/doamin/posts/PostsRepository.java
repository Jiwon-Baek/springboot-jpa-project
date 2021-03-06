package com.springboot.project.doamin.posts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {


    //전체 게시물 내림차순으로 정렬
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    //해당 회원 게시물만 정렬
    @Query(value ="FROM Posts  where user_id=:user_id")
    List<Posts> findByUserId(@Param("user_id")Long user_id);

}