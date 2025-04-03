package com.Project.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.domain.PaymentMethod;
import com.Project.trading.model.PaymentOrder;
import com.Project.trading.model.User;
import com.Project.trading.response.PaymentResponse;
import com.Project.trading.service.PaymentService;
import com.Project.trading.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(@RequestHeader("Authorization") String jwt, 
			@PathVariable PaymentMethod paymentMethod, 
			@PathVariable Long amount) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		PaymentResponse paymentResponse;
		
		PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
		
		if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			paymentResponse = paymentService.createRazorpayPaymentLink(user, amount, order.getId());
		}
		else {
			paymentResponse = paymentService.createStripePaymentLink(user, amount, amount);
		}
		
		return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
	}
	
	
}
