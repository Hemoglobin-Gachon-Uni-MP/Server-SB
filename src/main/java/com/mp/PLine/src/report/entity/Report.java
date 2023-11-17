package com.mp.PLine.src.report.entity;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.report.dto.PostReportReq;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    private Status status;

    public void reject() {
        this.status = Status.D;
    }

    public static Report of(Member member, Member toMember, PostReportReq postReportReq, Status status) {
        return Report.builder()
                .fromMember(member)
                .toMember(toMember)
                .category(postReportReq.getCategory())
                .feedOrCommentId(postReportReq.getFeedOrCommentId())
                .reason(postReportReq.getReason())
                .status(status)
                .build();
    }

    public static ReportResponseDto toReportResponse(Report report) {
        return ReportResponseDto.builder()
                .id(report.getFeedOrCommentId())
                .fromMember(report.getFromMember().getNickname())
                .toMember(report.getToMember().getNickname())
                .category(report.getCategory())
                .feedOrCommentId(report.getFeedOrCommentId())
                .reason(report.getReason())
                .build();
    }
}