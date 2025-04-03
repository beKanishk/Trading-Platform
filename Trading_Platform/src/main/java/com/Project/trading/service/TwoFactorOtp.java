package com.Project.trading.service;


import com.Project.trading.model.TwoFactorOTPModel;
import com.Project.trading.model.User;

public interface TwoFactorOtp {
	
	TwoFactorOTPModel crateTwoFactorOTP(User user, String otp, String jwt);
	
	TwoFactorOTPModel findByUser(Long userId);
	
	TwoFactorOTPModel findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTPModel twoFactorOTP, String otp);
	
	void deleteTwoFactorOTP(TwoFactorOTPModel twoFactorOTP);

}
