package com.mp.PLine.src.feed.repository;

import com.mp.PLine.src.feed.entity.Reply;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByIdAndStatus(@Param("id") Long id, @Param("status") Status status);
}
