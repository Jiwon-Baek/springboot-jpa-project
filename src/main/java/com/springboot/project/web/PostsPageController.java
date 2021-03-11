package com.springboot.project.web;


import com.springboot.project.config.auth.LoginUser;
import com.springboot.project.config.auth.dto.SessionUser;

import com.springboot.project.service.PostsService;

import com.springboot.project.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class PostsPageController {

    private final PostsService postsService;


    /*게시물 전체 출력*/
    @GetMapping("/posts")
    public String postsList(Model model, @LoginUser SessionUser user, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {


        model.addAttribute("posts", postsService.findPostsByPageRequest(pageable));

        model.addAttribute("pageList", postsService.getPageList(pageable));

        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }

        return "/posts/posts-list";
    }

    /*게시물 등록*/
    @GetMapping("/posts/save")
    public String postsSave(@LoginUser SessionUser user, Model model) {

        model.addAttribute("author", user.getUsername());

        return "posts/posts-save";
    }

    //게시물 세부 내용
    @GetMapping("/posts/detail/{id}")
    public String postsContent(@PathVariable Long id, Model model, @LoginUser SessionUser user) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("postD", dto);


        //로그인한 유저가 글을 작성한 유저와 다를 때 수정,삭제 버튼이 안뜨게 하기 위함
        if (user != null) {
            if (dto.getAuthor().equals(user.getUsername())) {
                model.addAttribute("postUser", user.getUsername());
            }
        }

        return "posts/posts-detail";
    }


    /*게시물 수정*/
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts/posts-update";
    }


    /*작성자명 검색 결과창*/
    @GetMapping("/posts/author/{author}")
    public String searchAuthor(Model model, @LoginUser SessionUser user, @PathVariable String author) {

        model.addAttribute("postsA", postsService.findByAuthor(author));

        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }


        return "/posts/posts-list";
    }

    /*제목명 검색 결과창*/
    @GetMapping("/posts/title/{title}")
    public String searchTitle(Model model, @LoginUser SessionUser user, @PathVariable String title) {

        model.addAttribute("postsT", postsService.findByTitle(title));

        if (user != null) {
            model.addAttribute("userId", user.getUsername());
        }

        return "/posts/posts-list";
    }

}
