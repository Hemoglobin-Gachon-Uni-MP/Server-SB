package com.mp.PLine.src.myPage;

import com.mp.PLine.S3Uploader;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.myPage.dto.res.GetCertificationRes;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.myPage.dto.req.PostCertificationReq;
import com.mp.PLine.src.myPage.dto.res.FeedRes;
import com.mp.PLine.src.myPage.dto.util.FeedResI;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.dto.res.GetMemberRes;
import com.mp.PLine.src.myPage.dto.req.PatchMemberReq;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

}
