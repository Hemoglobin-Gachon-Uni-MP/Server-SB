package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.dto.CommentResI;
import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.feed.entity.Feed;
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
    // 댓글 존재 여부 확인
    Optional<Comment> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // 게시물 댓글 리스트 반환
    @Query("select c.id as commentId, \n" +
            "   c.user.id as userId, c.user.profileImg as profileImg, c.user.nickname as nickname, \n" +
            "   c.context as context, c.createdAt as date \n" +
            "from Comment c \n" +
            " where c.feed.id = :feedId and c.status = 'A' and c.user.status = 'A'")
    List<CommentResI> findByFeedId(@Param("feedId") Long feedId);

    // 유저 삭제시 댓글도 삭제
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.user.id = :userId")
    void setCommentByUserStatus(@Param("userId") Long userId);

    // 게시물 삭제시 댓글도 삭제
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.feed.id = :feedId")
    void setCommentByFeedStatus(@Param("feedId") Long feedId);
}
