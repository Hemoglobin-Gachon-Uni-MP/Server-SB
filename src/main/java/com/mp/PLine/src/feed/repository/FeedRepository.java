package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.dto.util.GetFeedsResI;
import com.mp.PLine.src.myPage.dto.util.FeedResI;
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
public interface FeedRepository extends JpaRepository<Feed, Long> {
    // check feed existence
    Optional<Feed> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // return user's feed list
    @Query(value = "select f.id as feedId, \n" +
            "   f.member.id as memberId, f.member.nickname as nickname, f.member.profileImg as profileImg, \n" +
            "   f.context as context, \n" +
            "   (select count(*) from Comment c where c.feed.id = f.id and c.status = 'A') as replyCnt, \n" +
            "   (select count(*) from Reply r where r.feed.id = f.id and r.status = 'A') as commentCnt, \n" +
            "   function('date_format', f.createdAt, '%m/%d') as date, \n" +
            "   f.isReceiver as isReceiver \n" +
            "from Feed as f \n" +
            "where f.member.id = :memberId and f.status = :status and f.member.status = :status \n" +
            " order by f.createdAt desc",
    nativeQuery = true)
    List<FeedResI> findAllByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") Status status);

    // return all feed
    @Query(value = "select f.id as feedId," +
            "   f.member.id as memberId, f.user.profileImg as profileImg, f.member.nickname as nickname, \n" +
            "   f.context as context, \n" +
            "   (select count(*) from Comment c where c.feed.id = f.id and c.status = 'A') as replyCnt, \n" +
            "   (select count(*) from Reply r where r.feed.id = f.id and r.status = 'A') as commentCnt, \n" +
            "   function('date_format', f.createdAt, '%m/%d') as date, \n" +
            "   f.abo as abo, f.rh as rh, f.location as location, f.isReceiver as isReceiver \n" +
            "from Feed f \n" +
            "where f.status = 'A' and f.member.status = 'A' \n" +
            "order by f.createdAt desc",
    nativeQuery = true)
    List<GetFeedsResI> findAllByStatus();

    // delete user's feeds when deleting user
    @Modifying
    @Query("update Feed f set f.status = 'D' where f.member.id = :memberId")
    void setFeedByMemberStatus(@Param("memberId") Long memberId);
}