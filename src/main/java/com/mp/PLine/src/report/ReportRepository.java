package com.mp.PLine.src.report;

import com.mp.PLine.src.feed.dto.res.ReplyRes;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.report.entity.Report;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findAllByFromMemberIdAndStatus(Long memberId, Status status);

    @Query("select r from Report r \n" +
            "where r.fromMember.id = :fromMember and r.toMember.id = :toMember \n" +
            "   and r.category = 'F' and r.feedOrCommentId = :feedId \n" +
            "   and r.status = 'A'")
    Optional<Report> findReportedFeed(Long fromMember, Long toMember, Long feedId);
}