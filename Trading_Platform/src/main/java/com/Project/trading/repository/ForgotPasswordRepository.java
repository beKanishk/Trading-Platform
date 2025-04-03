package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String>{
	ForgotPasswordToken findByUserId(Long userId);
}
