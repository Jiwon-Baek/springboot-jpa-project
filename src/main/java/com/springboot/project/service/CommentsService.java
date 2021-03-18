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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CommentsListDto> findAllBypostId(Long postId) {

        return commentsRepository.findAllBypostId(postId).stream()
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


}
