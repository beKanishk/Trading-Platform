package com.Project.trading.serviceInterface;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.ForgotPasswordToken;
import com.Project.trading.model.User;

public interface ForgotPasswordServiceInterface {
	
	ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userID);
	
	void deleteToken(ForgotPasswordToken token);
}
