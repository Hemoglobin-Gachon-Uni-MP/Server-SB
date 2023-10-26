package com.mp.PLine.config.security.filter;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.member.MemberRepository;
import com.mp.PLine.src.member.entity.Member;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.config.Elements.JWT;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;
    private AuthenticationManager authenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

//        try {
//            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
//            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("secretKey")).build().verify(jwtToken);
//            Long id = decodedJWT.getClaim("id").asLong();
//            Member member = memberRepository.findById(id).
//                    orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_JWT));
//
//            PrincipalDetails principalDetails = new PrincipalDetails(user);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    principalDetails, null, principalDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (BaseException e) {
////            log.warn("[JwtAuthorizationFilter] token 파싱 실패 : {}", e.getMessage());
//        }
        chain.doFilter(request, response);
    }
}