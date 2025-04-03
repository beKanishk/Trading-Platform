package com.Project.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.model.PaymentDetails;
import com.Project.trading.model.User;
import com.Project.trading.repository.PaymentDetailsRepository;
import com.Project.trading.serviceInterface.PaymentDetailsServiceInterface;

@Service
public class PaymentDetailsService implements PaymentDetailsServiceInterface{

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	
	@Override
	public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
			String bankName, User user) {
		PaymentDetails paymentDetails = new PaymentDetails();
		
		paymentDetails.setAccountHolderName(accountHolderName);
		paymentDetails.setAccountNumber(accountNumber);
		paymentDetails.setBankName(bankName);
		paymentDetails.setIfsc(ifsc);
		paymentDetails.setUser(user);
		
		return paymentDetailsRepository.save(paymentDetails);
	}

	@Override
	public PaymentDetails getUserPaymentDetails(User user) {
		// TODO Auto-generated method stub
		return paymentDetailsRepository.findByUserId(user.getId());
	}

}
