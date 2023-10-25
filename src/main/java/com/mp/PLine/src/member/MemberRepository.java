package com.mp.PLine.src.member;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long> {
    // check user existence by kakaoId
    Optional<Member> findByKakaoIdAndStatus(@Param("kakaoId") Long kakaoId, @Param("status") Status A);

    // check user existence
    Optional<Member> findByIdAndStatus(@Param("memberId") Long memberId, @Param("status") Status A);
}
