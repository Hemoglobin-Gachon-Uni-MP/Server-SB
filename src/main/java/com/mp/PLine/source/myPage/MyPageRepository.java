package com.mp.PLine.source.myPage;

import com.mp.PLine.source.member.entity.Member;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MyPageRepository extends JpaRepository<Member, Long> {
    // check user existence
    Optional<Member> findByIdAndStatus(@Param("memberId") Long memberId, @Param("status") Status A);
}
