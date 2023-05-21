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
    // 답글 존재 여부 확인
    Optional<Reply> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // 답글 리스트 반환
    @Query("select r.id as replyId, \n" +
            "   r.user.id as userId, r.user.profileImg as profileImg, r.user.nickname as nickname, \n" +
            "   r.context as context, \n" +
            "   r.createdAt as date \n" +
            "from Reply r \n" +
            "where r.comment.id = :commentId and r.status = 'A' and r.user.status = 'A'")
    List<ReplyResI> findByCommentId(@Param("commentId") Long commentId);

    // 유저 삭제시 답글도 삭제
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.user.id = :userId")
    void setReplyByUserStatus(@Param("userId") Long userId);

    // 게시물 삭제시 답글도 삭제
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.feed.id = :feedId")
    void setReplyByFeedStatus(@Param("feedId") Long feedId);

    // 댓글 삭제시 답글도 삭제
    @Modifying
    @Query("update Reply r set r.status = 'D' where r.comment.id = :commentId")
    void setReplyByCommentStatus(@Param("commentId") Long commentId);
}
