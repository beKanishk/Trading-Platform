package com.Project.trading.serviceInterface;

import com.Project.trading.model.Order;
import com.Project.trading.model.User;
import com.Project.trading.model.Wallet;

public interface WalletServiceInterface {
	Wallet getUserWallet(User user);
	
	Wallet addBalance(Wallet wallet, Long amount);
	
	Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
	
	Wallet findWalletById(Long id) throws Exception;
	
	Wallet payOrderPayment(Order order, User user) throws Exception;
}
