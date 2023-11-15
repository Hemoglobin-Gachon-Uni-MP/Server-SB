package com.mp.PLine.utils;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.security.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.mp.PLine.config.BaseResponseStatus.EMPTY_JWT;
import static com.mp.PLine.config.BaseResponseStatus.INVALID_JWT;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Getter
@Slf4j
@Service
public class JwtService {
    private static final String accessHeader = "Authorization";
    private static final String refreshHeader = "Authorization-refresh";
    private static final JwkProvider provider = new JwkProviderBuilder("https://kauth.kakao.com")
            .cached(10, 7, TimeUnit.DAYS) // 7일간 최대 10개 캐시
            .build();

    public String getAccessHeader() {
        return accessHeader;
    }

    public String getRefreshHeader() {
        return refreshHeader;
    }

    /**
     * Create JWT
     * @return String
     */
    public String createAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
//                .claim("email", email)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY.getBytes())
                .compact();
    }

    public String createAccessToken(String adminKey) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
//                .claim("email", email)
                .claim("key", adminKey)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY.getBytes())
                .compact();
    }

    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY.getBytes())
                .compact();
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
     * AccessToken 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER.getValue()))
                .map(refreshToken -> refreshToken.replace(BEARER.getValue(), ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER.getValue()))
                .map(refreshToken -> refreshToken.replace(BEARER.getValue(), ""));
    }

    public Optional<Long> extractSocialId(String token) {
        try {
            // 검증없이 디코딩
            DecodedJWT jwtOrigin = JWT.decode(token);

            // 공개키 프로바이더 준비 => 싱긑톤으로 구현
            Jwk jwk = provider.get(jwtOrigin.getKeyId());

            // 검증 및 디코딩
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            // "sub" 값을 추출하여 반환
            Long socialId = Long.valueOf(jwt.getClaim("sub").asString());
            return Optional.ofNullable(socialId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * AccessToken에서 UserId 추출
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<Long> extractUserId(String accessToken) {
        try {
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(Secret.JWT_ACCESS_TOKEN_KEY.getBytes()) // 시크릿 키 설정
                    .build()
                    .parseClaimsJws(accessToken); // 액세스 토큰 파싱

            Claims claims = jwt.getBody();
            return Optional.ofNullable(claims.get("userId", Long.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public Optional<String> extractAdminKey(String accessToken) {
        try {
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(Secret.JWT_ACCESS_TOKEN_KEY.getBytes()) // 시크릿 키 설정
                    .build()
                    .parseClaimsJws(accessToken); // 액세스 토큰 파싱

            Claims claims = jwt.getBody();
            return Optional.ofNullable(claims.get("key", String.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("어드민 액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Secret.JWT_ACCESS_TOKEN_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public Long getMemberId() throws BaseException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = extractToken(request)
                .orElseThrow(() -> new BaseException(EMPTY_JWT));
        jwtToken = jwtToken.substring("Bearer ".length());
        return extractUserId(jwtToken)
                .orElseThrow(() -> new BaseException(INVALID_JWT));
    }

    public static Optional<String> extractToken(HttpServletRequest request) {
        String token = request.getHeader(accessHeader);
        if (isEmptyAuthorizationHeader(token)) {
            return Optional.empty();
        }

        return Optional.of(token);
    }

    private static boolean isEmptyAuthorizationHeader(String token) {
        return !StringUtils.hasText(token);
    }

}
