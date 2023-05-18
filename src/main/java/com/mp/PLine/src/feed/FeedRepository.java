package com.mp.PLine.src.feed;

import com.mp.PLine.src.feed.dto.FeedRes;
import com.mp.PLine.src.feed.entity.Feed;
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
public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);

    @Query(value = "select f.id as feedId, \n" +
            "   f.user.id as userId, f.user.nickname as nickname, f.user.profileImg as profileImg, \n" +
            "   f.context as context, \n" +
            "   (select count(*) from Comment c where c.feed.id = f.id and c.status = :status) as commentCnt, \n" +
            "   function('date_format', f.createdAt, '%m/%d') as date, \n" +
            "   f.isReceiver as isReceiver \n" +
            "from Feed as f \n" +
            "where (f.user.id = :userId and f.status = :status and f.user.status = :status)")
    List<FeedRes> findAllByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Status status);
}