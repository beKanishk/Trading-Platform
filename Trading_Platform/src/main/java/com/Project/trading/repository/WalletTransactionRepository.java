package com.Project.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{
	List<WalletTransaction> findByUserId(Long id);
}
