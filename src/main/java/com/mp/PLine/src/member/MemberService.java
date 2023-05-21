package com.mp.PLine.src.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.member.dto.PostUserReq;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /** 카카오 **/

    /* 카카오 A.T 받기 (프론트가 없는 경우) */
    public String getKaKaoAccessToken(String code) {
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=add1a9f7abd5d86e163dbcc6fad723d5"); // REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9000/kakao"); // 인가코드 받은 redirect_uri 입력
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
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

    /* Access Token을 사용하여 사용자 정보 받아오기 */
    public Long createKakaoUser(String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String id = "";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            // JSON 파싱
            JsonElement element = JsonParser.parseString(result.toString());
            id = element.getAsJsonObject().get("id").toString();

            System.out.println("User ID : " + id);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Long.parseLong(id);
    }

    /* 카카오 회원가입 API */
    @Transactional
    public Long signUp(PostUserReq info, Long kakaoId, Long age) throws BaseException {
        // 카카오 ID를 이용해서 존재하는 유저인지 확인
        if(memberRepository.findByKakaoIdAndStatus(kakaoId, Status.A).isPresent()) {
            throw new BaseException(BaseResponseStatus.EXIST_USER);
        }

        // 존재하지 않는 유저라면 DB에 저장
        Member newMember = new Member(info.getName(), info.getNickname(), info.getBirth(), age, info.getPhone(),
            info.getGender(), info.getAbo(), info.getRh(), info.getLocation(), info.getProfileImg(), kakaoId, Status.A);
        Member savedMember = memberRepository.save(newMember);

        return savedMember.getId();
    }

    /* 회원 탈퇴 API */
    @Transactional
    public String resign(Long userId) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = memberRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        member.get().setStatus(Status.D);
        memberRepository.save(member.get());



        return "회원 탈퇴가 완료되었습니다.";
    }
}
