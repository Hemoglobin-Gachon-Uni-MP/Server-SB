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
    // 피드 존재 여부 확인
    Optional<Feed> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    // 유저가 올린 피드 리스트 반환
    @Query(value = "select f.id as feedId, \n" +
            "   f.user.id as userId, f.user.nickname as nickname, f.user.profileImg as profileImg, \n" +
            "   f.context as context, \n" +
            "   (select count(*) from Comment c where c.feed.id = f.id and c.status = 'A') as replyCnt, \n" +
            "   (select count(*) from Reply r where r.feed.id = f.id and r.status = 'A') as commentCnt, \n" +
            "   function('date_format', f.createdAt, '%m/%d') as date, \n" +
            "   f.isReceiver as isReceiver \n" +
            "from Feed as f \n" +
            "where f.user.id = :userId and f.status = :status and f.user.status = :status \n" +
            " order by f.createdAt desc ")
    List<FeedResI> findAllByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Status status);

    // 게시물 목록 반환
    @Query(value = "select f.id as feedId," +
            "   f.user.id as userId, f.user.profileImg as profileImg, f.user.nickname as nickname, \n" +
            "   f.context as context, \n" +
            "   (select count(*) from Comment c where c.feed.id = f.id and c.status = 'A') as replyCnt, \n" +
            "   (select count(*) from Reply r where r.feed.id = f.id and r.status = 'A') as commentCnt, \n" +
            "   function('date_format', f.createdAt, '%m/%d') as date, \n" +
            "   f.abo as abo, f.rh as rh, f.location as location, f.isReceiver as isReceiver \n" +
            "from Feed f \n" +
            "where f.status = 'A' and f.user.status = 'A' \n" +
            "order by f.createdAt desc ")
    List<GetFeedsResI> findAllByStatus();

    // 유저 삭제시 게시물도 삭제
    @Modifying
    @Query("update Feed f set f.status = 'D' where f.user.id = :userId")
    void setFeedByUserStatus(@Param("userId") Long userId);
}