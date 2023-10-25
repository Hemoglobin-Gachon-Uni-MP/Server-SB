package com.mp.PLine.src.admin;

import com.mp.PLine.src.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface AdminRepository extends JpaRepository<Admin, Long> {

}