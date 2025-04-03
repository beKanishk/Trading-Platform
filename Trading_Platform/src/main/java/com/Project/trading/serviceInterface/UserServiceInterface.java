package com.Project.trading.serviceInterface;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.User;

public interface UserServiceInterface {
	
	public User findUserProfileByJwt(String jwt) throws Exception;
	public User findUserByEmail(String email) throws Exception;
	public User findUserById(Long id) throws Exception;
	
	public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
	
	User updatePassword(User user, String newPassword);
}
