package com.mp.PLine.src.feed;

import com.mp.PLine.src.feed.dto.FeedResI;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select f.id as feedId, \n" +
            "   f.user.id as userId, f.user.nickname as nickname, f.user.profileImg as profileImg, \n" +
            "   f.context as context, \n" +
            "   count(c.id) as commentCnt, \n" +
            "   from_unixtime(f.createdAt, '%m/%d') as date \n" +
            "from Feed as f \n" +
            "   left join Comment as c on c.feed.id = f.id \n" +
            "where (f.user.id = :userId and f.status = :status)")
    List<FeedResI> findAllByUserIdAndStatus(Long userId, Status status);
}