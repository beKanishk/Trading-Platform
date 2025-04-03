package com.Project.trading.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.OrderType;
import com.Project.trading.model.Order;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;
import com.Project.trading.repository.WalletRepository;
import com.Project.trading.serviceInterface.WalletServiceInterface;

@Service
public class WalletService implements WalletServiceInterface{

	@Autowired
	WalletRepository walletRepository;
	
	@Override
	public Wallet getUserWallet(User user) {
		Wallet wallet = walletRepository.findByUserId(user.getId());
		
		if(wallet == null) {
			wallet = new Wallet();
			wallet.setUser(user);
		}
		walletRepository.save(wallet);
		return wallet;
	}

	@Override
	public Wallet addBalance(Wallet wallet, Long amount) {
		BigDecimal balance = wallet.getBalance();
		BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));
		
		wallet.setBalance(newBalance);
		return walletRepository.save(wallet);
	}

	@Override
	public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
		Wallet senderWallet = getUserWallet(sender);
		
		if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
			throw new Exception("Insufficient Balance");
		}
		
		BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
		senderWallet.setBalance(senderBalance);
		walletRepository.save(senderWallet);
		
		BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
		receiverWallet.setBalance(receiverBalance);
		walletRepository.save(receiverWallet);
		
		return senderWallet;
	}

	@Override
	public Wallet findWalletById(Long id) throws Exception {
		Optional<Wallet> wallet = walletRepository.findById(id);
		if(wallet.isPresent()) {
			return wallet.get();
		}
		throw new Exception("wallet not found");
	}

	@Override
	public Wallet payOrderPayment(Order order, User user) throws Exception {
		Wallet wallet =  getUserWallet(user);
		
		if(order.getOrderType().equals(OrderType.BUY)) {
			BigDecimal newBalance = wallet.getBalance().subtract(order.getAmount());
			if(newBalance.compareTo(order.getAmount()) < 0) {
				throw new Exception("Insufficient funds for this transaction");
			}
			
			wallet.setBalance(newBalance);
		}
		else {
			BigDecimal newBalance = wallet.getBalance().add(order.getAmount());
			wallet.setBalance(newBalance);
		}
		return walletRepository.save(wallet);
	}
	
}
