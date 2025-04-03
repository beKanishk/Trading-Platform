package com.Project.trading.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.model.TwoFactorOTPModel;
import com.Project.trading.model.User;
import com.Project.trading.repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOTPService implements TwoFactorOtp{

	@Autowired
	TwoFactorOtpRepository twoFactorOtpRepository;
	
	@Override
	public TwoFactorOTPModel crateTwoFactorOTP(User user, String otp, String jwt) {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		
		TwoFactorOTPModel twoFactorOTPModel = new TwoFactorOTPModel();
		twoFactorOTPModel.setId(id);
		twoFactorOTPModel.setJwt(jwt);
		twoFactorOTPModel.setOtp(otp);
		twoFactorOTPModel.setUser(user);
		
		return twoFactorOtpRepository.save(twoFactorOTPModel);
	}

	@Override
	public TwoFactorOTPModel findByUser(Long userId) {
		return twoFactorOtpRepository.findByUserId(userId);
	}

	@Override
	public TwoFactorOTPModel findById(String id) {
		Optional<TwoFactorOTPModel> otp = twoFactorOtpRepository.findById(id);
		return otp.orElse(null);
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOTPModel twoFactorOTP, String otp) {
		// TODO Auto-generated method stub
		return twoFactorOTP.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOTP(TwoFactorOTPModel twoFactorOTP) {
		twoFactorOtpRepository.delete(twoFactorOTP);
		
	}

}
