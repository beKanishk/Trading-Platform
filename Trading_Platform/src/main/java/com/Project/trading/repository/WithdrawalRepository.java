package com.Project.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long>{
	List<Withdrawal> findByUserId(Long userId);
}
