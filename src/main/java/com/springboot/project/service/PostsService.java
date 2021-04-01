package com.springboot.project.service;

import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.posts.PostsRepository;
import com.springboot.project.doamin.user.User;
import com.springboot.project.doamin.user.UserRepository;
import com.springboot.project.web.dto.*;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //글 세부내용 클릭 후 조회수 증가
    @Transactional(readOnly = true)
    public PostsResponseDto findAllByIdAndHit(Long id) throws Exception {

        postsRepository.updateHit(id);


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
    @Transactional
    public List<PostsListDto> findByUserId(Long userId,Pageable pageable) {
        return postsRepository.findAllByUserId(userId,pageable).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    //검색한 작성자명과 같은 작성자 게시물 출력
    @Transactional
    public List<PostsListDto> findByAuthor(String author, Pageable pageable) {

        return postsRepository.findByAuthorContains(author, pageable).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    //검색한 제목과 같은 제목 게시물 출력
    @Transactional
    public List<PostsListDto> findByTitle(String title, Pageable pageable) {

        return postsRepository.findByTitleContains(title, pageable).stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());

    }

    /*게시판 페이지 서비스*/

    @Transactional
    public Page<Posts> findPostsByPageRequest(Pageable pageable) {

        return postsRepository.findAll(pageable);
    }

    //게시물 전체 갯수를 구하는 서비스
    @Transactional
    public Long getPostsCount() {
        return postsRepository.count();
    }


    //게시물 전체 갯수를 활용하여 필요한 페이지 버튼 수를 구해 활용
    @Transactional
    public List<PageDto> getPageList(Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getPostsCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / pageable.getPageSize())));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > pageable.getPageNumber() + 10)
                ? pageable.getPageNumber() + 10
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        Integer curPageNum = (pageable.getPageNumber() < 3) ? 1 : pageable.getPageNumber() - 2;


        // 페이지 번호 할당
        for (int i = 0; i < blockLastPageNum; i++) {

            pageDtos.add(curPageNum);
            curPageNum++;

        }

        return pageDtos.stream().map(PageDto::new).collect(Collectors.toList());

    }

    //제목으로 검색해 해당되는 게시물 갯수
    @Transactional
    public Long getPostsSearchTitleCount(String title) {

        return postsRepository.findCountByTitleContains(title);
    }


    //제목으로 검색해 해당되는 게시물 갯수를 활용하여 페이지번호 계산
    @Transactional
    public List<PageDto> getSearchTitlePageList(String title, Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getPostsSearchTitleCount(title));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / pageable.getPageSize())));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > pageable.getPageNumber() + 10)
                ? pageable.getPageNumber() + 10
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        Integer curPageNum = (pageable.getPageNumber() < 3) ? 1 : pageable.getPageNumber() - 2;


        // 페이지 번호 할당
        for (int i = 0; i < blockLastPageNum; i++) {

            pageDtos.add(curPageNum);
            curPageNum++;

        }

        return pageDtos.stream().map(PageDto::new).collect(Collectors.toList());

    }


    //작성자로 검색해 해당되는 게시물 갯수
    @Transactional
    public Long getPostsSearchAuthorCount(String author) {

        return postsRepository.findCountByAuthorContains(author);
    }


    //작성자로 검색해 해당되는 게시물 갯수를 활용하여 페이지번호 계산
    @Transactional
    public List<PageDto> getSearchAuthorPageList(String author, Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getPostsSearchAuthorCount(author));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / pageable.getPageSize())));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > pageable.getPageNumber() + 10)
                ? pageable.getPageNumber() + 10
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        Integer curPageNum = (pageable.getPageNumber() < 3) ? 1 : pageable.getPageNumber() - 2;


        // 페이지 번호 할당
        for (int i = 0; i < blockLastPageNum; i++) {

            pageDtos.add(curPageNum);
            curPageNum++;

        }

        return pageDtos.stream().map(PageDto::new).collect(Collectors.toList());

    }


    @Transactional
    public Long getPostsSearchUserCount(Long userId) {

        return postsRepository.findCountByUserId(userId);
    }



    @Transactional
    public List<PageDto> getSearchUserPageList(Long userId, Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getPostsSearchUserCount(userId));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / pageable.getPageSize())));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > pageable.getPageNumber() + 10)
                ? pageable.getPageNumber() + 10
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        Integer curPageNum = (pageable.getPageNumber() < 3) ? 1 : pageable.getPageNumber() - 2;


        // 페이지 번호 할당
        for (int i = 0; i < blockLastPageNum; i++) {

            pageDtos.add(curPageNum);
            curPageNum++;

        }

        return pageDtos.stream().map(PageDto::new).collect(Collectors.toList());

    }



}