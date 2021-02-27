package com.springboot.project.web;

import com.springboot.project.web.dto.PostsListDto;
import com.springboot.project.web.dto.PostsResponseDto;
import com.springboot.project.web.dto.PostsSaveDto;
import com.springboot.project.web.dto.PostsUpdateDto;
import com.springboot.project.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveDto postsSaveDto) {

        return postsService.save(postsSaveDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {

        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListDto> findAll() {

        return postsService.findAllDesc();
    }
}