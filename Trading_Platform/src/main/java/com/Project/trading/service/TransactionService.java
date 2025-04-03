package com.Project.trading.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.WalletTransactionType;
import com.Project.trading.model.Transaction;
import com.Project.trading.model.Wallet;
import com.Project.trading.repository.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    public List<Transaction> getTransactionByWallet(Wallet wallet) {
    	System.out.println("Saved Transaction: " + transactionRepository.findAll());
    	return transactionRepository.findByWalletId(wallet.getId());
    }


	public Transaction createTransaction(Wallet userWallet, WalletTransactionType transactionType, Long transactionId,
			String message, Long amount) {
		Transaction transaction = new Transaction();
        
        transaction.setWallet(userWallet);
        transaction.setWalletTransactionType(transactionType);
        transaction.setId(transactionId);
        transaction.setPurpose(message);
        transaction.setAmount(amount);
        transaction.setDateTime(LocalDateTime.now());  // Setting current date & time
        

        return transactionRepository.save(transaction);
	}
}
