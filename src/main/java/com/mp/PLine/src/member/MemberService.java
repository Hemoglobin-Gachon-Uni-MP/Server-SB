package com.mp.PLine.src.member;

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
import com.mp.PLine.src.myPage.CertificationRepository;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /* Sign up with kakao API */
    @Transactional
    public PostMemberRes signUp(PostMemberReq info) throws BaseException {
        Member member = memberRepository.findBySocialId(jwtService.extractSocialId(info.getIdToken())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_SOCIAL_ID_TOKEN)))
                .orElseThrow(() -> new BaseException(BaseResponseStatus.EXIST_USER));
        int profile = (int) (Math.random() * 2) + 1;
        String profileImg = "";

        if (profile == 1) {
            profileImg = "https://p-line.s3.ap-northeast-2.amazonaws.com/profile/Group+20.png";
        } else if(profile == 2) {
            profileImg = "https://p-line.s3.ap-northeast-2.amazonaws.com/profile/Group+21.png";
        }
        member.signUpMember(info, Member.parseAge(info.getBirth()), profileImg);
        return Member.toPostMemberRes(jwtService.createAccessToken(member.getId()), member.getId());
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

    @Transactional
    public BaseResponse<PostMemberRes> findMember(LoginRequestDto loginDto) throws BaseException {
        Long id = jwtService.extractSocialId(loginDto.getIdToken())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_SOCIAL_ID_TOKEN));
        Member member = memberRepository.findBySocialId(id)
                .orElseThrow(() -> {
                    // 처음 접근한 사용자는 소셜아이디를 저장
                    memberRepository.save(Member.setEmptyMember(id));
                    return new BaseException(BaseResponseStatus.INVALID_USER);
                });
        Long memberId = member.getId();
        return new BaseResponse<>(new PostMemberRes(jwtService.createAccessToken(memberId), memberId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}