package com.cotato_hackathon.article5.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cotato_hackathon.article5.config.HttpUtil;
import com.cotato_hackathon.article5.entity.senior.Role;
import com.cotato_hackathon.article5.repository.SeniorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final SeniorRepository seniorRepository;

    private final RedisTemplate redisTemplate;

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create() // JWT 토큰을 생성하는 빌더 반환
                .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정

                //클레임으로는 저희는 email 하나만 사용합니다.
                //추가적으로 식별자나, 이름 등의 정보를 더 추가하셔도 됩니다.
                //추가하실 경우 .withClaim(클래임 이름, 클래임 값) 으로 설정해주시면 됩니다
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
    }

    /**
     * RefreshToken 생성
     * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    // AccessToken + RefreshToken 실어서 보내기
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);

        makeJsonResponse(response, data);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    // 회원가입 전이라는 것을 프론트에게 알려주기 위해 Role이 Guest임을 넘겨줌
    public void sendAccessAndRefreshTokenBeforeSignup(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);
        data.put("role", Role.GUEST);

        makeJsonResponse(response, data);
        log.info("신규 회원을 위한 Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(EMAIL_CLAIM) // claim(Email) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }
    public Optional<Long> getExpiration(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            Optional<Date> expiration = Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getExpiresAt());
            Long now = new Date().getTime();
            return Optional.of(expiration.get().getTime() - now);
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    public void updateRefreshToken(String email, String refreshToken) {
        seniorRepository.findByEmail(email)
                .ifPresentOrElse(
                        senior -> {
                            senior.updateRefreshToken(refreshToken);
                            seniorRepository.saveAndFlush(senior);
                            // redis에 저장
                            redisTemplate.opsForValue().set(email, refreshToken, refreshTokenExpirationPeriod, TimeUnit.SECONDS);
                        },
                        () -> {
                            throw new RuntimeException();
                        }
                );
    }

    /**
     * Token이 Valid한지 확인
     */
    public boolean isTokenValid(String token) {
        JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        String isLogout = (String) redisTemplate.opsForValue().get(token);
        if(isLogout == null) return true; // 로그아웃 한 회원이 아니라면
        else return false;
//        else throw new AllGlinsException(ErrorCode.INVALID_TOKEN_LOGOUT, ErrorCode.INVALID_TOKEN_LOGOUT.getMessage());
    }

    public String logout(){
        HttpServletRequest request = HttpUtil.getRequest();
        String accessToken = this.extractAccessToken(request).get();
        System.out.println("accessToken = " + accessToken);

        // 1. Access Token 검증
        if (!this.isTokenValid(accessToken)) {
            throw new RuntimeException();
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        String userEmail = this.extractEmail(accessToken).get();
        System.out.println("userEmail = " + userEmail);

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get(userEmail) != null) {
            System.out.println("redisTemplate.opsForValue().get(userEmail) = " + redisTemplate.opsForValue().get(userEmail));
            // Refresh Token 삭제
            redisTemplate.delete(userEmail);
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = this.getExpiration(accessToken).get();
        System.out.println("expiration = " + expiration);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return "로그아웃 성공";
    }

    private void makeJsonResponse(HttpServletResponse response, Map<String, Object> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }
}
