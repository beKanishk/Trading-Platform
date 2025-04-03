package com.Project.trading.serviceInterface;

import java.util.List;

import com.Project.trading.model.User;
import com.Project.trading.model.Withdrawal;

public interface WithdrawalServiceInterface {
	Withdrawal requestWithdrawal(Long amount, User user);
	
	Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
	
	List<Withdrawal> getUserWithdrawalHistory(User user);
	
	List<Withdrawal> getAllWithdrawalsRequest();
}
