package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.dto.util.ReplyResI;
import com.mp.PLine.src.feed.entity.Reply;
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
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // check reply existence
    Optional<Reply> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // return feed's comment list
    @Query("select r.id as replyId, \n" +
            "   r.member.id as memberId, r.member.profileImg as profileImg, r.member.nickname as nickname, \n" +
            "   r.context as context, \n" +
            "   r.createdAt as date, \n" +
            "   case when((select count(*) from Report re \n" +
            "           where re.fromMember.id = :memberId and re.toMember.id = r.member.id \n" +
            "           and re.feedOrCommentId = r.id and re.category = 'R' and re.status = 'A') > 0) then true \n" +
            "        else false \n" +
            "   end as isReportedFromUser \n" +
            "from Reply r \n" +
            "where r.comment.id = :commentId and r.status = 'A' and r.member.status = 'A' \n" +
            "order by r.createdAt")
    List<ReplyResI> findByCommentId(@Param("memberId") Long memberId, @Param("commentId") Long commentId);

    List<Reply> findByCommentId(Long commentId);

    // delete user's replies when deleting user
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.member.id = :memberId")
    void setReplyByMemberStatus(@Param("memberId") Long memberId);

    // delete feed's replies when deleting feed
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.feed.id = :feedId")
    void setReplyByFeedStatus(@Param("feedId") Long feedId);

    // delete comment's replies when deleting comment
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.comment.id = :commentId")
    void setReplyByCommentStatus(@Param("commentId") Long commentId);

    Optional<List<Reply>> findAllByFeedId(Long feedId);
}
