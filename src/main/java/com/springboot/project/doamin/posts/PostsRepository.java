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
    @Query(value = "FROM Posts WHERE user_id=:user_id ORDER BY id DESC")
    List<Posts> findByUserId(@Param("user_id") Long user_id);

    //제목으로 게시물 찾아 정렬
    //Repository에서 By뒤에 Contains가 오면 Like 절을 직접 작성 안 하고 사용가능
    List<Posts> findByTitleContains(String title);

    //작성자명으로 게시물 찾아 정렬
    List<Posts> findByAuthorContains(String author);
}