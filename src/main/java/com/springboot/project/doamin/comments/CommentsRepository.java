package com.springboot.project.doamin.comments;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT c FROM Comments c where post_id=:post_id")
    List<Comments> findAllBypostId(@Param("post_id") Long post_id);
}
