package com.cotato_hackathon.article5.auth.oauth.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        Map<String, Object> account = getKakaoAccount(attributes, "kakao_account");
        Map<String, Object> profile = getKakaoAccount(account, "profile");

        if (account == null || profile == null) {
            return null;
        }

        return (String) profile.get("nickname");
    }

    private Map<String, Object> getKakaoAccount(Map<String, Object> attributes, String kakaoAccount) {
        return (Map<String, Object>) attributes.get(kakaoAccount);
    }
}
