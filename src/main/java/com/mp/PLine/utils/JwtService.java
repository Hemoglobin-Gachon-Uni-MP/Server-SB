package com.mp.PLine.utils;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.security.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.mp.PLine.config.BaseResponseStatus.EMPTY_JWT;
import static com.mp.PLine.config.BaseResponseStatus.INVALID_JWT;

@Getter
@Slf4j
@Service
public class JwtService {
    private final String accessHeader = "Authorization";
    private final String refreshHeader = "Authorization-refresh";

    /**
     * Create JWT
     * @return String
     */
    public String createAccessToken(String email){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("email", email)
//                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY)
                .compact();
    }

//    public String createRefreshToken(Long userId){
//        Date now = new Date();
//        return Jwts.builder()
//                .setHeaderParam("type","jwt")
//                .setIssuedAt(now)
//                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
//                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY)
//                .compact();
//    }

    /* Get JWT from header */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /* get userId from JWT */
    public Long getMemberId() throws BaseException {
        // 1. get JWT
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_ACCESS_TOKEN_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. get userId
        return claims.getBody().get("userId", Long.class);
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


}
