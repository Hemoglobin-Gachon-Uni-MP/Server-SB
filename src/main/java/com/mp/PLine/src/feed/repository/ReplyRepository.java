package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
