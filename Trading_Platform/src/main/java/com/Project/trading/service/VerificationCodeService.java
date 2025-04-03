package com.Project.trading.service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.User;
import com.Project.trading.model.VerificationCode;
import com.Project.trading.repository.VerificationCodeRepository;
import com.Project.trading.serviceInterface.VerificationCodeServiceInterface;
import com.Project.trading.utils.OtpUtils;

@Service
public class VerificationCodeService implements VerificationCodeServiceInterface{

	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
	@Override
	public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
		VerificationCode verificationCode = new VerificationCode();
		
		verificationCode.setOtp(OtpUtils.generateOtp());
		verificationCode.setUser(user);
		verificationCode.setVerificationType(verificationType);
		
		return verificationCodeRepository.save(verificationCode);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) throws Exception {
		Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
		
		if(verificationCode.isPresent()) {
			return verificationCode.get();
		}
		throw new Exception("verification code not found");
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userId) throws Exception {
		VerificationCode verificationCode = verificationCodeRepository.findByUserId(userId);
		if(verificationCode != null) {
			return verificationCode;
		}
		throw new Exception("verification code not found");
	}

	@Override
	public void deleteVerificationCodeById(VerificationCode verificationCode) {
		verificationCodeRepository.delete(verificationCode);
	}
	
}
