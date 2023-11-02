package com.mp.PLine.source.feed.repository;

import com.mp.PLine.source.feed.dto.util.GetFeedsResI;
import com.mp.PLine.source.myPage.dto.util.FeedResI;
import com.mp.PLine.source.feed.entity.Feed;
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
public interface FeedRepository extends JpaRepository<Feed, Long> {
    // check feed existence
    Optional<Feed> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // return user's feed list
    @Query(value = "SELECT f.id AS feedId, \n" +
            "f.member_id AS memberId, m.nickname AS nickname, m.profile_img AS profileImg, \n" +
            "f.context AS context, \n" +
            "(SELECT COUNT(*) FROM Comment c WHERE c.feed_id = f.id AND c.status = 'A') AS replyCnt, \n" +
            "(SELECT COUNT(*) FROM Reply r WHERE r.feed_id = f.id AND r.status = 'A') AS commentCnt, \n" +
            "FORMATDATETIME(f.created_at, 'MM/dd') AS date, \n" +
            "f.is_receiver AS isReceiver \n" +
            "FROM feed AS f \n" +
            "INNER JOIN member AS m ON f.member_id = m.id \n" +
            "WHERE f.member_id = :memberId AND f.status = :status AND m.status = :status \n" +
            "ORDER BY f.created_at DESC", nativeQuery = true)
    List<FeedResI> findAllByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") Status status);


    // return all feed
    @Query(value = "SELECT f.id AS feedId, \n" +
            "f.member_id AS memberId, m.profile_img AS profileImg, m.nickname AS nickname, \n" +
            "f.context AS context, \n" +
            "(SELECT COUNT(*) FROM Comment c WHERE c.feed_id = f.id AND c.status = 'A') AS replyCnt, \n" +
            "(SELECT COUNT(*) FROM Reply r WHERE r.feed_id = f.id AND r.status = 'A') AS commentCnt, \n" +
            "FORMATDATETIME(f.created_at, 'MM/dd') AS date, \n" +
            "f.abo AS abo, f.rh AS rh, f.location AS location, f.is_receiver AS isReceiver \n" +
            "FROM feed AS f \n" +
            "INNER JOIN member AS m ON f.member_id = m.id \n" +
            "WHERE f.status = 'A' AND m.status = 'A' \n" +
            "ORDER BY f.created_at DESC", nativeQuery = true)
    List<GetFeedsResI> findAllByStatus();


    // delete user's feeds when deleting user
    @Modifying
    @Query("update Feed f set f.status = 'D' where f.member.id = :memberId")
    void setFeedByMemberStatus(@Param("memberId") Long memberId);
}