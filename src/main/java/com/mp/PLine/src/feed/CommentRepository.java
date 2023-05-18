package com.mp.PLine.src.feed;

import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
