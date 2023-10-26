package com.mp.PLine.src.admin;

import com.mp.PLine.S3Uploader;
import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.dto.PostCertificationReq;
import com.mp.PLine.src.admin.entity.Certification;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageRepository;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;
    private final CertificationRepository certificationRepository;
    private final MyPageRepository myPageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long uploadDonation(Long memberId, MultipartFile image, PostCertificationReq request) throws BaseException, IOException {
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        if(image.isEmpty()) throw new BaseException(BaseResponseStatus.POST_CERTIFICATION_EMPTY_IMAGE);

        String fileUrl = s3Uploader.upload(image,"image");
        Certification certifications = certificationRepository.save(
                Certification.of(member, request.getCertificationNum(), fileUrl, request.getDate(), Status.A));

        return certifications.getId();
    }

    public List<Certification> getDonationLists(Long memberId) throws BaseException {
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        return certificationRepository.findAllByMemberIdAndStatus(memberId, Status.A);
    }


}
