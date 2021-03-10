package com.springboot.project.service;

import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.posts.PostsRepository;
import com.springboot.project.doamin.user.User;
import com.springboot.project.doamin.user.UserRepository;
import com.springboot.project.web.dto.PostsListDto;
import com.springboot.project.web.dto.PostsResponseDto;
import com.springboot.project.web.dto.PostsSaveDto;
import com.springboot.project.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;


    //게시물 저장
    @Transactional
    public Long save(PostsSaveDto postsSaveDto) {

        User user = userRepository.findByUsername(postsSaveDto.getAuthor());
        //작성자의 회원번호를 같이 저장하여 조인
        postsSaveDto.setUser(user);

        return postsRepository.save(postsSaveDto.toEntity()).getId();

    }

    //게시물 수정
    @Transactional
    public Long update(Long id, PostsUpdateDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    //게시물 삭제
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    //글번호로 게시물 찾기
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {

        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    //글번호 역순 출력
    @Transactional(readOnly = true)
    public List<PostsListDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());
    }

    //유저의 고유 번호로 게시물 찾기(연동)
    @Transactional(readOnly = true)
    public List<PostsListDto> findByUserId(Long userId) {
        return postsRepository.findByUserId(userId).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    //검색한 작성자명과 같은 작성자 게시물 출력
    @Transactional
    public List<PostsListDto> findByAuthor(String author) {

        return postsRepository.findByAuthorContains(author).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    //검색한 제목과 같은 제목 게시물 출력
    @Transactional
    public List<PostsListDto> findByTitle(String title) {

        return postsRepository.findByTitleContains(title).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    //게시판 페이지 서비스

    @Transactional
    public Page<Posts> findBooksByPageRequest(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }
}