package com.cotato_hackathon.article5.auth.oauth.handler;

import com.cotato_hackathon.article5.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // HTTP 응답 상태 코드를 설정합니다.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // HTTP 응답 헤더를 설정합니다.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // HTTP 응답 본문을 생성합니다.
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED,
                "authorization failed");
        // HTTP 응답 본문을 JSON 형태로 변환합니다.
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);
        // HTTP 응답 본문을 전송합니다.
        response.getWriter().write(json);
    }
}