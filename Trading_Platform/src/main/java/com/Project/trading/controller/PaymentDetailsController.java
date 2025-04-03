package com.Project.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.model.PaymentDetails;
import com.Project.trading.model.User;
import com.Project.trading.service.PaymentDetailsService;
import com.Project.trading.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentDetailsService paymentDetailsService;
	
	@PostMapping("/add/payment-details")
	public ResponseEntity<PaymentDetails> addPaymentDetails(@RequestHeader("Authorization") String jwt,
			@RequestBody PaymentDetails request) throws Exception{
		User user  = userService.findUserProfileByJwt(jwt);
		PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(request.getAccountNumber(), 
				request.getAccountHolderName(), 
				request.getIfsc(), 
				request.getBankName(), 
				user);
		
		return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
	}
	
	@GetMapping("/payment-details")
	public ResponseEntity<PaymentDetails> getUserPaymentDetails(@RequestHeader("Authorization") String jwt) throws Exception{
		User user  = userService.findUserProfileByJwt(jwt);
		
		PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);
		return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
	}
}
