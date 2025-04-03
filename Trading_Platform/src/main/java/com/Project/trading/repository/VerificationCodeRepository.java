package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{
	public VerificationCode findByUserId(Long userId);
}
