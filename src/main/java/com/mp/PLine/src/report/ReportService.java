package com.mp.PLine.src.report;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.repository.CommentRepository;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.feed.repository.ReplyRepository;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageRepository;
import com.mp.PLine.src.report.dto.PostReportReq;
import com.mp.PLine.src.report.entity.Report;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final ReportRepository reportRepository;
    private final MyPageRepository myPageRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    /* Report API */
    @Transactional
    public String report(Long memberId, PostReportReq postReportReq) throws BaseException {
        // verify user existence using userID
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        Member reportedMember = myPageRepository.findByIdAndStatus(postReportReq.getReportedMemberId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT_USER));

        if(Objects.equals(postReportReq.getMemberId(), postReportReq.getReportedMemberId())) throw new BaseException(BaseResponseStatus.INVALID_REPORT_SAME_USER);

        // 존재하는 게시물, 댓글, 답글인지 확인
        String category = postReportReq.getCategory();
        switch (category) {
            case "F":
                feedRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
                break;
            case "C":
                commentRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT));
                break;
            case "R":
                replyRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPLY));
                break;
        }

        // 이미 신고한 게시물일 경우 예외 처리
        if(reportRepository.findReport(memberId, reportedMember.getId(), postReportReq.getCategory(), postReportReq.getFeedOrCommentId()).isPresent())
            throw new BaseException(BaseResponseStatus.INVALID_EXIST_REPORT);

        reportRepository.save(Report.of(member, reportedMember, postReportReq, Status.A));  // 신고 완료
        return "신고가 완료되었습니다.";
    }

    /* Delete Report API */
    @Transactional
    public String deleteReport(Long memberId, PostReportReq postReportReq) throws BaseException {
        // verify user existence using userID
        Member member = myPageRepository.findByIdAndStatus(memberId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        Member reportedMember = myPageRepository.findByIdAndStatus(postReportReq.getReportedMemberId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT_USER));

        if(Objects.equals(postReportReq.getMemberId(), postReportReq.getReportedMemberId())) throw new BaseException(BaseResponseStatus.INVALID_REPORT_SAME_USER);

        // 존재하는 게시물, 댓글, 답글인지 확인
        String category = postReportReq.getCategory();
        switch (category) {
            case "F":
                feedRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
                break;
            case "C":
                commentRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT));
                break;
            case "R":
                replyRepository.findByIdAndStatus(postReportReq.getFeedOrCommentId(), Status.A)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPLY));
                break;
        }

        Report report = reportRepository.findReport(memberId, reportedMember.getId(), postReportReq.getCategory(), postReportReq.getFeedOrCommentId())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.INVALID_NON_EXIST_REPORT));

        report.setStatus(Status.D);
        return "신고 취소가 완료되었습니다.";
    }
}
