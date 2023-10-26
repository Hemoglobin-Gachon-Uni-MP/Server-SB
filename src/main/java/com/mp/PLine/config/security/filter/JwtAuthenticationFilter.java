package com.mp.PLine.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.security.config.Elements.JWT;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

//        try {
//            ObjectMapper om = new ObjectMapper();
//            LoginRequest loginInfo = om.readValue(request.getInputStream(), LoginRequest.class);
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginInfo.getLoginId(), loginInfo.getPassword());
//
//            return authenticationManager.authenticate(authenticationToken);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//
//        String jwtToken = JWT.create()
//                .withSubject("ILuvIt")
//                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 60 * 1)))
//                .withClaim("id", principalDetails.getUser().getId())
//                .sign(Algorithm.HMAC512("secretKey"));
//        response.addHeader("Authorization", "Bearer " + jwtToken);
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}