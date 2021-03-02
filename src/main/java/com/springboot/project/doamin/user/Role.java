package com.springboot.project.doamin.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.DoubleStream;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_USER","관리자");

    private final String key;
    private final String title;


}