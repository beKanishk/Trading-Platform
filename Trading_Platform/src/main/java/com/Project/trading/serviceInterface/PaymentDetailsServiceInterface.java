package com.Project.trading.serviceInterface;

import com.Project.trading.model.PaymentDetails;
import com.Project.trading.model.User;

public interface PaymentDetailsServiceInterface {
	public PaymentDetails addPaymentDetails(String accountNumber, 
			String accountHolderName, 
			String ifsc, 
			String bankName, 
			User user);
	
	public PaymentDetails getUserPaymentDetails(User user);
}
