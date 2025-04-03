package com.Project.trading.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.WithdrawalStatus;
import com.Project.trading.model.User;
import com.Project.trading.model.Withdrawal;
import com.Project.trading.repository.WithdrawalRepository;
import com.Project.trading.serviceInterface.WithdrawalServiceInterface;

@Service
public class WithdrawalService implements WithdrawalServiceInterface{
	@Autowired
	private WithdrawalRepository withdrawalRepository;
	
	@Override
	public Withdrawal requestWithdrawal(Long amount, User user) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAmount(amount);
		withdrawal.setUser(user);
		withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
		withdrawal.setDate(LocalDateTime.now());
		return withdrawalRepository.save(withdrawal);
	}

	@Override
	public Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception {
		Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
		if(withdrawal.isEmpty()) {
			throw new Exception("Withdrawal not found");
		}
		
		Withdrawal withdrawal1 = withdrawal.get();
		
		if(accept) {
			withdrawal1.setWithdrawalStatus(WithdrawalStatus.SUCCESS);
		}
		else {
			withdrawal1.setWithdrawalStatus(WithdrawalStatus.DECLINE);
		}
		return withdrawalRepository.save(withdrawal1);
	}

	@Override
	public List<Withdrawal> getUserWithdrawalHistory(User user) {
		return withdrawalRepository.findByUserId(user.getId());
	}

	@Override
	public List<Withdrawal> getAllWithdrawalsRequest() {
		return withdrawalRepository.findAll();
	}
	
}
