package com.mp.PLine.src.report.entity;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.report.dto.PostReportReq;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Report extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "from_member")  // 신고한 멤버
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member")  // 신고당한 멤버
    private Member toMember;

    private String category;  // 신고 카테고리 (게시물, 댓글)
    private Long feedOrCommentId;
    private String reason;
    private Boolean processStatus;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @Builder
//    public Report(Member fromMember, Member toMember, String category,
//                  Long feedOrCommentId, String reason, Status status) {
//        this.fromMember = fromMember;
//        this.toMember = toMember;
//        this.category = category;
//        this.feedOrCommentId = feedOrCommentId;
//        this.reason = reason;
//        this.status = status;
//    }

    public void execute() {
        this.processStatus = true;
    }

    public static Report of(Member member, Member toMember, PostReportReq postReportReq, Status status) {
        return Report.builder()
                .fromMember(member)
                .toMember(toMember)
                .category(postReportReq.getCategory())
                .feedOrCommentId(postReportReq.getFeedOrCommentId())
                .reason(postReportReq.getReason())
                .processStatus(false)
                .status(status)
                .build();
    }

    public static ReportResponseDto.ReportReadResponseDto toReportResponse(Report report) {
        return ReportResponseDto.ReportReadResponseDto.builder()
                .fromMember(report.getFromMember().getNickname())
                .toMember(report.getToMember().getNickname())
                .category(report.getCategory())
                .feedOrCommentId(report.getFeedOrCommentId())
                .reason(report.getReason())
                .build();
    }
}