package com.mp.PLine.src.myPage;

import com.mp.PLine.S3Uploader;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.myPage.dto.res.*;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.myPage.dto.req.PostCertificationReq;
import com.mp.PLine.src.myPage.dto.util.FeedResI;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.dto.req.PatchMemberReq;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final FeedRepository feedRepository;

    private final CertificationRepository certificationRepository;
    private final S3Uploader s3Uploader;

    /* convert blood to string */
    public static String blood(int rh, int abo) {
        String blood = "";
        if(rh == 0) blood += "Rh+";
        else if(rh == 1) blood += "Rh-";

        if(abo == 0) blood += "A";
        else if(abo == 1) blood += "B";
        else if(abo == 2) blood += "O";
        else if(abo == 3) blood += "AB";

        return blood;
    }

    /* Return user information API */
    public GetMemberRes getMember(Long memberId) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        List<FeedResI> feedList = feedRepository.findAllByMemberIdAndStatus(memberId, Status.A);
        List<FeedRes> feedRes = feedList.stream()
                .map(FeedRes::from)
                .collect(Collectors.toList());

        return GetMemberRes.of(member, member.getGender().equals("F") ? "여" : "남", feedRes);
    }

    /* Edit user information API */
    @Transactional
    public String updateMember(Long memberId, PatchMemberReq patchMemberReq) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        member.setNickname(patchMemberReq.getNickname());
        member.setLocation(patchMemberReq.getLocation());
        return "정보가 수정되었습니다.";
    }

    /* Upload certification API */
    @Transactional
    public Long uploadDonation(Long memberId, MultipartFile image, PostCertificationReq request) throws BaseException, IOException {
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        // 이름, 이미지 확인 진행
        if(!request.getName().equals(member.getName())) throw new BaseException(BaseResponseStatus.POST_CERTIFICATION_INVALID_NAME);
        if(image.isEmpty()) throw new BaseException(BaseResponseStatus.POST_CERTIFICATION_EMPTY_IMAGE);

        String fileUrl = s3Uploader.upload(image,"image");
        Certification certifications = certificationRepository.save(
                Certification.of(member, request.getCertificationNum(), fileUrl, request.getDate(), Status.A));

        return certifications.getId();
    }

    /* Return certification list API */
    public List<GetCertificationRes> getCertificationList(Long memberId) throws BaseException {
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        List<Certification> certificationList = certificationRepository.findAllByMemberIdAndStatus(memberId, Status.A);

        return certificationList.stream()
                .map(d -> GetCertificationRes.builder()
                        .certificationId(d.getId())
                        .memberId(memberId)
                        .name(member.getName())
                        .certificationNum(d.getCertificateNumber())
                        .date(d.getDate()).build())
                .collect(Collectors.toList());
    }

    /* Return certification reward list API */
    public GetRewardRes getRewardList(Long memberId) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        List<Certification> certificationList = certificationRepository.findAllByMemberIdAndStatus(memberId, Status.A);
        long length = certificationList.size();

        List<RewardRes> rewardList = new ArrayList<>();
        // 여러 방법을 고민해봤는데.... 메달 개수가 적어서 이렇게 하드코딩하는 게 제일 성능이 베스트일 것 같아 이렇게 했습니다... 배열을 만드는 것도 애매하고... 다 애매했도다
        if(1 <= length) rewardList.add(RewardRes.from("헌혈 입문자",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(0).getCreatedAt())));
        if(3 <= length) rewardList.add(RewardRes.from("생명의 씨앗",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(2).getCreatedAt())));
        if(6 <= length) rewardList.add(RewardRes.from("헌혈하는 기쁨",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(5).getCreatedAt())));
        if(9 <= length) rewardList.add(RewardRes.from("생명의 빛",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(8).getCreatedAt())));
        if(12 <= length) rewardList.add(RewardRes.from("우리 모두의 힘",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(11).getCreatedAt())));
        if(15 <= length) rewardList.add(RewardRes.from("헌혈 히어로",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(14).getCreatedAt())));
        if(18 <= length) rewardList.add(RewardRes.from("헌혈 마스터",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(17).getCreatedAt())));
        if(21 <= length) rewardList.add(RewardRes.from("헌혈의 전설",
                "https://p-line.s3.ap-northeast-2.amazonaws.com/medal/medal1.png", date(certificationList.get(20).getCreatedAt())));

        return GetRewardRes.of(member, length, rewardList);
    }

    private String date(Timestamp createdAt) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(createdAt);
    }
}
