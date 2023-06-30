package com.cotato_hackathon.article5.auth.oauth.handler;

import com.cotato_hackathon.article5.entity.senior.CustomOAuth2User;
import com.cotato_hackathon.article5.entity.senior.Role;
import com.cotato_hackathon.article5.entity.senior.Senior;
import com.cotato_hackathon.article5.service.JwtService;
import com.cotato_hackathon.article5.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor // 컴포넌트 스캔을 통해 자동으로 빈(bean)으로 등록해줌
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ValidateService validateService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        Senior senior = validateService.validateMemberByEmail(oAuth2User.getEmail());
        if(senior.getRole().equals(Role.USER)){ // 이미 회원가입 된 경우
            System.out.println("이미 회원가입 한 유저입니다.");
            String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
            String refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
            return;
        }
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        jwtService.sendAccessAndRefreshTokenBeforeSignup(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}