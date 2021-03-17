package com.springboot.project.doamin.posts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {


    //전체 게시물 내림차순으로 정렬
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    //해당 회원 게시물만 정렬
    Page<Posts> findAllByUserId(@Param("user_id") Long user_id, Pageable pageable);

    @Query("SELECT count(id) FROM Posts WHERE user_id =:user_id")
    Long findCountByUserId(@Param("user_id") Long user_id);

    //제목으로 게시물 찾아 정렬
    //Repository에서 By뒤에 Contains가 오면 Like 절을 직접 작성 안 하고 사용가능
    Page<Posts> findByTitleContains(String title, Pageable pageable);

    @Query("SELECT COUNT(p.id) FROM Posts p WHERE title LIKE %:title%")
    Long findCountByTitleContains(@Param("title") String title);

    //작성자명으로 게시물 찾아 정렬
    Page<Posts> findByAuthorContains(String author, Pageable pageable);

    @Query("SELECT COUNT(p.id) FROM Posts p WHERE author LIKE %:author%")
    Long findCountByAuthorContains(@Param("author") String author);


    //조회수 쿼리
    @Modifying
    @Query(value = "update Posts set hit = hit+1 where id =:id", nativeQuery = true)
    void updateHit(@Param("id") Long id) throws Exception;

}