package com.mp.PLine.src.myPage;

import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByMemberIdAndStatus(Long memberId, Status status);

    // delete user's feeds when deleting user
    @Modifying
    @Query("update Certification c set c.status = 'D' where c.member.id = :memberId")
    void setCertificationByMemberStatus(@Param("memberId") Long memberId);

    List<Certification> findAllByStatus(Status status);
}