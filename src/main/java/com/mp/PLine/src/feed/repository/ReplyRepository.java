package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.dto.ReplyRes;
import com.mp.PLine.src.feed.entity.Reply;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
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
            "   replace(function('date_format', r.createdAt, '%m/%d %p %l:%i'))  as date \n" +
            "from Reply r \n" +
            "where r.comment.id = :commentId and r.status = 'A' and r.user.status = 'A'")
    List<ReplyRes> findByCommentId(@Param("commentId") Long commentId);
}
