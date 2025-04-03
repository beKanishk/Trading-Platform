package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.TwoFactorOTPModel;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTPModel, String>{
	TwoFactorOTPModel findByUserId(Long userId);
}
