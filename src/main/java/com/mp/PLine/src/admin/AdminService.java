package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.dto.AdminDto;
import com.mp.PLine.src.admin.entity.Admin;
import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.feed.entity.Reply;
import com.mp.PLine.src.feed.repository.CommentRepository;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.feed.repository.ReplyRepository;
import com.mp.PLine.src.myPage.CertificationRepository;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.report.ReportRepository;
import com.mp.PLine.src.report.dto.CertificationResponseDto;
import com.mp.PLine.src.report.dto.ReportRequestDto;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import com.mp.PLine.src.report.entity.Report;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final ReportRepository reportRepository;
    private final CertificationRepository certificationRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void signUp(AdminDto.RequestDto adminRequest) throws BaseException {
        if (!adminRequest.getKey().startsWith("a")) {
            throw new BaseException(BaseResponseStatus.INVALID_ADMIN_KEY);
        }
        adminRepository.save(Admin.from(adminRequest.getKey()));
    }

    public AdminDto.ResponseDto login(AdminDto.RequestDto adminRequest) throws BaseException {
        Admin admin = adminRepository.findAdminByAdminKey(adminRequest.getKey())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ADMIN_ACCOUNT));
        return new AdminDto.ResponseDto(jwtService.createAccessToken(admin.getAdminKey()));
    }

    public List<ReportResponseDto> readReports(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return reportRepository.findAllByStatus(Status.A, pageable)
                .stream()
                .map(Report::toReportResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void executeFeedReport(ReportRequestDto.FeedReportDto reportDto) throws BaseException {
        Long feedId = reportDto.getFeedId();
        Report report = reportRepository.findById(reportDto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        if (reportDto.getCategory().equals("F")) {
            Feed feed = feedRepository.findById(reportDto.getFeedId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
            feed.delete();
            commentRepository.findAllByFeedId(feedId)
                    .ifPresent(comments -> comments.forEach(Comment::delete));
            replyRepository.findAllByFeedId(feedId).
                    ifPresent(replies -> replies.forEach(Reply::delete));
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_REPORT_CATEGORY);
        }
        report.reject();
    }

    @Transactional
    public void executeCommentsReport(ReportRequestDto.CommentReportDto reportDto) throws BaseException {
        Report report = reportRepository.findById(reportDto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        if (reportDto.getCategory().equals("C")) {
            Comment comment =  commentRepository.findById(reportDto.getCommentId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT));
            comment.delete();
            replyRepository.findByCommentId(reportDto.getCommentId())
                    .forEach(Reply::delete);
        } else if (reportDto.getCategory().equals("R")) {
            Reply reply = replyRepository.findById(reportDto.getCommentId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPLY));
            reply.delete();
        }
        report.reject();
    }

    @Transactional
    public void rejectReport(Long id) throws BaseException {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        report.reject();
    }

    public List<CertificationResponseDto> readCertifications() {
        return certificationRepository.findAllByStatus(Status.A).stream()
                .map(Certification::toCertificationResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void executeCertification(Long id) throws BaseException {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        certification.excecute();
    }

    @Transactional
    public void rejectCertification(Long id) throws BaseException {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        certification.reject();
    }
}
