package com.springboot.project.doamin.comments;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT c FROM Comments c where post_id=:post_id ORDER BY c.id DESC")
    Page<Comments>  findAllBypostId(@Param("post_id") Long post_id, Pageable pageable);

    @Query("SELECT c FROM Comments c where user_id=:user_id")
    Comments findByuserId(@Param("user_id") Long user_id);
}
