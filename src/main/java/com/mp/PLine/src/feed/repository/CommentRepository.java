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
            "   c.user.id as userId, c.user.profileImg as profileImg, c.user.nickname as nickname, \n" +
            "   c.context as context, c.createdAt as date \n" +
            "from Comment c \n" +
            "where c.feed.id = :feedId and c.status = 'A' and c.user.status = 'A' \n" +
            "order by c.createdAt")
    List<CommentResI> findByFeedId(@Param("feedId") Long feedId);

    // delete user's comments when deleting user
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.user.id = :userId")
    void setCommentByUserStatus(@Param("userId") Long userId);

    // delete feed's comments when deleting feed
    @Modifying
    @Query("update Comment c set c.status = 'D' where c.feed.id = :feedId")
    void setCommentByFeedStatus(@Param("feedId") Long feedId);
}
