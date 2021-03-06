package com.springboot.project.service;


import com.springboot.project.doamin.message.Message;
import com.springboot.project.doamin.message.MessageRepository;

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
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    /*메세지 페이지, 해당 사용자에게 보낸 것만 보기*/

    @Transactional
    public Page<Message> findMessageByPageRequest(String username, Pageable pageable) {

        Long user_id = userRepository.findByUsername(username).getId();

        return messageRepository.findAllByUserId(user_id, pageable);
    }

    //메세지 전체 갯수를 구하는 서비스
    @Transactional
    public Long getMessageCount() {

        return messageRepository.count();
    }


    //게시물 전체 갯수를 활용하여 필요한 페이지 버튼 수를 구해 활용
    @Transactional
    public List<PageDto> getPageList(Pageable pageable) {

        List<Integer> pageDtos = new ArrayList<>();

        // 총 게시글 갯수
        Double messageTotalCount = Double.valueOf(this.getMessageCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((messageTotalCount / pageable.getPageSize())));

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


    //메세지 보내기(디비 저장)
    @Transactional
    public Long save(MessageSendDto saveDto) {

        User user = userRepository.findByUsername(saveDto.getRecipients());

        saveDto.setUser(user);

        if (user == null) {

            throw new RuntimeException("없는 회원입니다.");

        }

        return messageRepository.save(saveDto.toEntity()).getId();

    }


    //글번호로 메세지(쪽지) 세부내용
    @Transactional(readOnly = true)
    public MessageResponseDto findById(Long id) throws Exception {


        Message entity = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메세지가 없습니다. id=" + id));


        messageRepository.readCheck(id);

        return new MessageResponseDto(entity);
    }

    //메세지 삭제
    @Transactional
    public void delete(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메세지가 없습니다. id=" + id));

        messageRepository.delete(message);
    }


    @Transactional
    public int countReadMessage(String username) {

        Long id = userRepository.findByUsername(username).getId();

        return messageRepository.countMessagesByUserRead(id);

    }
}
