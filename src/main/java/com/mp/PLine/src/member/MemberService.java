package com.mp.PLine.src.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.repository.CommentRepository;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.feed.repository.ReplyRepository;
import com.mp.PLine.src.login.dto.LoginRequestDto;
import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.src.member.dto.res.PostMemberRes;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.src.myPage.CertificationRepository;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final JwtService jwtService;
    private final CertificationRepository certificationRepository;

    /* Get AccessToken from kakao */
    public String getKaKaoAccessToken(String code) {
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set setDoOutput true for Post request
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // send parameter using stream that required for Post request
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            bw.flush();

            // success if code is 200
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // read response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            // create JsonParser
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    /* Get kakaoId using AccessToken and create user */
    public Long createKakaoUser(String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String id = "";

        // get user information using AccessToken
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            // success if code is 200
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // read response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            // get kakaoId from response
            JsonElement element = JsonParser.parseString(result.toString());
            id = element.getAsJsonObject().get("id").toString();

            System.out.println("User ID : " + id);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Long.parseLong(id);
    }

    /* Sign up with kakao API */
    @Transactional
    public String signUp(PostMemberReq info) throws BaseException {
        if (memberRepository.findBySocialId(info.getSocialId()).isPresent()) {
            throw new BaseException(BaseResponseStatus.EXIST_USER);
        }
        int profile = (int) (Math.random() * 2) + 1;
        String profileImg = "";

        if (profile == 1) {
            profileImg = "https://p-line.s3.ap-northeast-2.amazonaws.com/profile/Group+20.png";
        } else if(profile == 2) {
            profileImg = "https://p-line.s3.ap-northeast-2.amazonaws.com/profile/Group+21.png";
        }
        Member member = Member.of(info, Member.parseAge(info.getBirth()), profileImg);
        memberRepository.save(member);
        return jwtService.createAccessToken(member.getId());
    }

    /* Resign API */
    @Transactional
    public String resign(Long memberId) throws BaseException {
        // verify user existence using userId
        Member member = memberRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        member.setStatus(Status.D);
        feedRepository.setFeedByMemberStatus(memberId);
        commentRepository.setCommentByMemberStatus(memberId);
        replyRepository.setReplyByMemberStatus(memberId);
        certificationRepository.setCertificationByMemberStatus(memberId);

        return "회원 탈퇴가 완료되었습니다.";
    }

    public BaseResponse<PostMemberRes> findMember(LoginRequestDto.LoginDto loginDto) throws BaseException {
        Member member = memberRepository.findBySocialId(loginDto.getSocialId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        Long memberId = member.getId();
        return new BaseResponse<>(new PostMemberRes(jwtService.createAccessToken(memberId), memberId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}