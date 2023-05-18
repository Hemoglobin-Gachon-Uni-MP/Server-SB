package com.mp.PLine.utils;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.mp.PLine.config.BaseResponseStatus.EMPTY_JWT;
import static com.mp.PLine.config.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    /**
        JWT 생성
     @return String
     */
    public String createJwt(Long userId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+2*(1000L *60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_ACCESS_TOKEN_KEY)
                .compact();
    }

    /* Header에서 X-ACCESS-TOKEN 으로 JWT 추출 */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /* JWT에서 userIdx 추출 */
    public Long getUserId() throws BaseException {
        //1. JWT 추출
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

        // 3. userIdx 추출
        return claims.getBody().get("userId", Long.class);  // jwt 에서 userIdx를 추출합니다.
    }

}
