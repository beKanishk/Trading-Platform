package com.Project.trading.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.WalletTransactionType;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;
import com.Project.trading.model.WalletTransaction;
import com.Project.trading.repository.WalletTransactionRepository;

@Service
public class WalletTransactionService {
	@Autowired
	private WalletTransactionRepository walletTransactionRepository;
	
	public List<WalletTransaction> getAllWalletTransactions(User user){
		return walletTransactionRepository.findByUserId(user.getId());
	}
	
	public WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType transactionType, String transferId,
			String message, Long amount, User user) {
		WalletTransaction transaction = new WalletTransaction();
		
		transaction.setWallet(userWallet);
		transaction.setPurpose(message);
		transaction.setTransferId(transferId);
		transaction.setWalletTransactionType(transactionType);
		transaction.setAmount(amount);
		transaction.setUserId(user.getId());
		transaction.setDate(LocalDate.now());
		
		return walletTransactionRepository.save(transaction);
	}
}
