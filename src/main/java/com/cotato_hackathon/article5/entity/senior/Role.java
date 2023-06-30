package com.cotato_hackathon.article5.entity.senior;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    /* OAuth2 로그인 후 닉네임과 생일을 입력하기 전에는 GUEST ROLE을 부여
    정보 입력까지 마치면 USER ROLE을 부여 */
    GUEST("ROLE_GUEST"), USER("ROLE_USER");
    private final String key;
}
