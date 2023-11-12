package com.mp.PLine.src.admin;

import com.mp.PLine.src.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
