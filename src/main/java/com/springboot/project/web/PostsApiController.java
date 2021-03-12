package com.springboot.project.web;

import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.web.dto.PostsListDto;
import com.springboot.project.web.dto.PostsResponseDto;
import com.springboot.project.web.dto.PostsSaveDto;
import com.springboot.project.web.dto.PostsUpdateDto;
import com.springboot.project.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //게시물 저장
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveDto postsSaveDto) {

        return postsService.save(postsSaveDto);
    }

    //게시물 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto requestDto) {
        return postsService.update(id, requestDto);
    }

    //게시물 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    //게시물 번호로 게시물 찾기
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {

        return postsService.findById(id);
    }

    //게시물 전체 출력
    @GetMapping("/api/v1/posts/list")
    public List<PostsListDto> findAll() {

        return postsService.findAllDesc();
    }

    //제목으로 게시물 찾기
    @PostMapping("/api/v1/posts/searchTitle")
    public String findByTitle(@RequestBody PostsResponseDto dto,Pageable pageable) {
        String title = dto.getTitle();

        postsService.findByTitle(title,pageable);

        return title;
    }

    //작성자명으로 게시물 찾기
    @PostMapping("/api/v1/posts/searchAuthor")
    public String findByAuthor(@RequestBody PostsResponseDto dto,Pageable pageable) {

        String author = dto.getAuthor();

        postsService.findByAuthor(author,pageable);

        return author;
    }


    //페이징
    @RequestMapping("/posts")
    public Page<Posts> findPostsByPageRequest(final Pageable pageable) {
        return postsService.findPostsByPageRequest(pageable);
    }
}

