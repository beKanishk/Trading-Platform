package com.Project.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Project.trading.model.Transaction;
import com.Project.trading.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	@Query("SELECT t FROM Transaction t WHERE t.wallet.id = :walletId")
    List<Transaction> findByWalletId(@Param("walletId") Long walletId);

}
