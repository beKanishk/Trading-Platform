package com.Project.trading.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.domain.WalletTransactionType;
import com.Project.trading.model.Order;
import com.Project.trading.model.PaymentOrder;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;
import com.Project.trading.model.WalletTransaction;
import com.Project.trading.service.OrderService;
import com.Project.trading.service.PaymentService;
import com.Project.trading.service.TransactionService;
import com.Project.trading.service.UserService;
import com.Project.trading.service.WalletService;
import com.Project.trading.service.WalletTransactionService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private WalletTransactionService walletTransactionService;
	
	@GetMapping
	public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		Wallet wallet = walletService.getUserWallet(user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{walletId}/transfer")
	public ResponseEntity<Wallet> walletToWalletTransfer(
	        @RequestHeader("Authorization") String jwt,
	        @PathVariable Long walletId, 
	        @RequestBody WalletTransaction request) throws Exception {

	    User sendUser = userService.findUserProfileByJwt(jwt);
	    Wallet receiverWallet = walletService.findWalletById(walletId);
	    Wallet wallet = walletService.walletToWalletTransfer(sendUser, receiverWallet, request.getAmount());

	    String transferId = request.getTransferId();
	    transactionService.createTransaction(receiverWallet, WalletTransactionType.ADD_MONEY, null, request.getPurpose(), request.getAmount());
	    walletTransactionService.createWalletTransaction(receiverWallet, WalletTransactionType.WALLET_TRANSFER, transferId, request.getPurpose(), request.getAmount(), sendUser);

	    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/{orderId}/pay")
	public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderId) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.getOrderById(orderId);
		
		Wallet wallet = walletService.payOrderPayment(order, user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/deposit")
	public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
			@RequestParam Long orderId, 
			@RequestParam String paymentId) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		Wallet wallet = walletService.getUserWallet(user);
		
		PaymentOrder order = paymentService.getPaymentOrderById(orderId);
		
		Boolean status = paymentService.proceedPaymentOrder(order, paymentId);
		
		if(wallet.getBalance() == null) {
			wallet.setBalance(BigDecimal.valueOf(0));
		}
		
		if(status) {
			wallet = walletService.addBalance(wallet, order.getAmount());
//			walletTransactionService.createWalletTransaction(wallet, WalletTransactionType.ADD_MONEY, paymentId, request.getPurpose(), request.getAmount());
		}
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
}
