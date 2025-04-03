package com.Project.trading.serviceInterface;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.User;
import com.Project.trading.model.VerificationCode;

public interface VerificationCodeServiceInterface {
	VerificationCode sendVerificationCode(User user, VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId) throws Exception;
	
	void deleteVerificationCodeById(VerificationCode verificationCode);
}
