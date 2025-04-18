package com.Project.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.domain.WalletTransactionType;
import com.Project.trading.model.Transaction;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;
import com.Project.trading.model.WalletTransaction;
import com.Project.trading.model.Withdrawal;
import com.Project.trading.service.TransactionService;
import com.Project.trading.service.UserService;
import com.Project.trading.service.WalletService;
import com.Project.trading.service.WithdrawalService;

@RestController
@RequestMapping("/api")
public class WithdrawalController {
	@Autowired
	private WithdrawalService withdrawalService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("withdrawal/{amount}")
	public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Wallet userWallet = walletService.getUserWallet(user);
		
		Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
		
		walletService.addBalance(userWallet, -withdrawal.getAmount());
		
		transactionService.createTransaction(userWallet, 
				WalletTransactionType.WITHDRAWL, 
				null, 
				"bank account withdrawal", 
				withdrawal.getAmount());
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	//for admin as admin will confirm it
	@PostMapping("/admin/withdrawal/{id}/proceed/{accept}")
	public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, 
			@PathVariable boolean accept, 
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);
		Wallet userWallet = walletService.getUserWallet(user);
		if(!accept) {
			walletService.addBalance(userWallet, withdrawal.getAmount());
		}
		
		
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	@GetMapping("/withdrawal")
	public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		List<Withdrawal> withdrawals = withdrawalService.getUserWithdrawalHistory(user);
		
		return new ResponseEntity<>(withdrawals, HttpStatus.OK);
	}
	
	@GetMapping("admin/withdrawal")
	public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) 
			throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalsRequest();
		
		return new ResponseEntity<>(withdrawals, HttpStatus.OK);
	}
}
