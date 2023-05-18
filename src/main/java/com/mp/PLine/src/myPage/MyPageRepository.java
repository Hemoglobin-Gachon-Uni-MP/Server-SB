package com.mp.PLine.src.myPage;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MyPageRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndStatus(Long userId, Status A);
}
