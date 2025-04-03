package com.Project.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.model.Transaction;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;
import com.Project.trading.model.WalletTransaction;
import com.Project.trading.service.TransactionService;
import com.Project.trading.service.UserService;
import com.Project.trading.service.WalletService;
import com.Project.trading.service.WalletTransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private WalletTransactionService walletTransactionService;
	
	@GetMapping
	public ResponseEntity<List<Transaction>> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		Wallet wallet = walletService.getUserWallet(user);
		List<Transaction> transactionList = transactionService.getTransactionByWallet(wallet);
		
		return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/wallet")
	public ResponseEntity<List<WalletTransaction>> getWalletTransaction(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		//Wallet wallet = walletService.getUserWallet(user);
		List<WalletTransaction> transactionList = walletTransactionService.getAllWalletTransactions(user);
		
		return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
	}
}
