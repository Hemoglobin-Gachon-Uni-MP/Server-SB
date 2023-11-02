package com.mp.PLine.source.admin.entity;

import com.mp.PLine.source.member.entity.Member;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Report(Member fromMember, Member toMember, String category,
                  Long feedOrCommentId, String reason, Status status) {
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.category = category;
        this.feedOrCommentId = feedOrCommentId;
        this.reason = reason;
        this.status = status;
    }

    public static Report of(Member fromMember, Member toMember, String category,
                            Long feedOrCommentId, String reason, Status status) {
        return Report.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .category(category)
                .feedOrCommentId(feedOrCommentId)
                .reason(reason)
                .status(status)
                .build();
    }
}