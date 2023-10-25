package com.mp.PLine.src.admin;

import com.mp.PLine.src.admin.entity.Certification;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByMemberIdAndStatus(Long memberId, Status status);
}