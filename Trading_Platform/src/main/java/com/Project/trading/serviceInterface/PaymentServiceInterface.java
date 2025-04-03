package com.Project.trading.serviceInterface;

import com.Project.trading.domain.PaymentMethod;
import com.Project.trading.model.PaymentOrder;
import com.Project.trading.model.User;
import com.Project.trading.response.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentServiceInterface {
	PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
	
	PaymentOrder getPaymentOrderById(Long id) throws Exception;
	
	Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
	
	PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws Exception;

	PaymentResponse createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;
}
