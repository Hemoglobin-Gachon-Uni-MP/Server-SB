package com.mp.PLine.src.report;

import com.mp.PLine.src.report.entity.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r \n" +
            "where r.fromMember.id = :fromMember and r.toMember.id = :toMember \n" +
            "   and r.category = :category and r.feedOrCommentId = :id and r.status ='A'")
    Optional<Report> findReport(@Param("fromMember") Long fromMember, @Param("toMember") Long toMember, @Param("category") String category, @Param("id")Long id);

    @Query("select r from Report r \n" +
            "where r.fromMember.id = :fromMember and r.toMember.id = :toMember \n" +
            "   and r.category = 'F' and r.feedOrCommentId = :feedId \n" +
            "   and r.status = 'A'")
    Optional<Report> findReportedFeed(@Param("fromMember") Long fromMember, @Param("toMember") Long toMember, @Param("feedId")Long feedId);

    List<Report> findAllByIsProcessedFalse(Pageable pageable);
}