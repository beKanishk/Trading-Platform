package com.Project.trading.serviceInterface;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.ForgotPasswordToken;
import com.Project.trading.model.User;
import com.Project.trading.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordService implements ForgotPasswordServiceInterface{
	
	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Override
	public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
		ForgotPasswordToken token = new ForgotPasswordToken();
		
		token.setSendTo(sendTo);
		token.setId(id);
		token.setUser(user);
		token.setOtp(otp);
		token.setVerificationType(verificationType);
		
		return forgotPasswordRepository.save(token);
	}

	@Override
	public ForgotPasswordToken findById(String id) {
		Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
		return token.orElse(null);
	}

	@Override
	public ForgotPasswordToken findByUser(Long userID) {
		// TODO Auto-generated method stub
		return forgotPasswordRepository.findByUserId(userID);
	}

	@Override
	public void deleteToken(ForgotPasswordToken token) {
		forgotPasswordRepository.delete(token);
		
	}
}
