package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.dto.util.CommentResI;
import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // check comment existence
    Optional<Comment> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // return comment list for feed
    @Query("select c.id as commentId, \n" +
            "   c.member.id as memberId, c.member.profileImg as profileImg, c.member.nickname as nickname, \n" +
            "   c.context as context, c.createdAt as date, \n" +
            "   case when((select count(*) from Report r \n" +
            "           where r.fromMember.id = :memberId and r.toMember.id = c.member.id \n" +
            "           and r.feedOrCommentId = c.id and r.category = 'C' and r.status = 'A') > 0) then true \n" +
            "        else false \n" +
            "   end as isReportedFromUser \n" +
            "from Comment c \n" +
            "where c.feed.id = :feedId and c.status = 'A' and c.member.status = 'A' \n" +
            "order by c.createdAt")
    List<CommentResI> findAllByFeedId(@Param("memberId") Long memberId, @Param("feedId") Long feedId);

    // delete user's comments when deleting user
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.member.id = :memberId")
    void setCommentByMemberStatus(@Param("memberId") Long memberId);

    // delete feed's comments when deleting feed
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.feed.id = :feedId")
    void setCommentByFeedStatus(@Param("feedId") Long feedId);

    Optional<List<Comment>> findAllByFeedId(Long feedId);

    List<Comment> findAllByFeedIdAndStatus(Long feedId, Status status);
}
