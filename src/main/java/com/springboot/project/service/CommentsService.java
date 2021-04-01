package com.springboot.project.service;

import com.springboot.project.doamin.comments.Comments;
import com.springboot.project.doamin.comments.CommentsRepository;
import com.springboot.project.doamin.posts.Posts;
import com.springboot.project.doamin.posts.PostsRepository;
import com.springboot.project.doamin.user.User;
import com.springboot.project.doamin.user.UserRepository;
import com.springboot.project.web.dto.CommentsListDto;

import com.springboot.project.web.dto.CommentsSaveDto;

import com.springboot.project.web.dto.CommentsUpdateDto;

import com.springboot.project.web.dto.PageDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    //댓글 저장
    @Transactional
    public Long save(CommentsSaveDto saveDto, Long postId, String username) {

        User user = userRepository.findByUsername(username);
        Posts posts = postsRepository.findForConmmentById(postId);


        saveDto.setUser(user);
        saveDto.setPosts(posts);

        return commentsRepository.save(saveDto.toEntity()).getId();

    }

    //댓글 출력
    @Transactional
    public List<CommentsListDto> findAllBypostId(Long postId, Pageable pageable) {

        return commentsRepository.findAllBypostId(postId,pageable).stream()
                .map(CommentsListDto::new)
                .collect(Collectors.toList());
    }

    //댓글 수정
    @Transactional
    public Long update(Long id, CommentsUpdateDto requestDto) {
        Comments comments = commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        comments.update(requestDto.getContent());

        return id;
    }


    //댓글 삭제
    @Transactional
    public void delete(Long id) {
        Comments comments = commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        commentsRepository.delete(comments);
    }

    //댓글 전체 갯수를 구하는 서비스
    @Transactional
    public Long getCommentsCount() {
        return commentsRepository.count();
    }


    //게시물 전체 갯수를 활용하여 필요한 페이지 버튼 수를 구해 활용
    @Transactional
    public List<PageDto> getPageList(Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 댓글 갯수
        Double CommentsTotalCount = Double.valueOf(this.getCommentsCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((CommentsTotalCount / pageable.getPageSize())));

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
