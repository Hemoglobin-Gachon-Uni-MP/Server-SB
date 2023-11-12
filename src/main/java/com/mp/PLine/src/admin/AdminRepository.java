package com.mp.PLine.src.admin;

import com.mp.PLine.src.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByAdminKey(String key);
}
