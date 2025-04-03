package com.Project.trading.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.domain.VerificationType;
import com.Project.trading.model.ForgotPasswordToken;
import com.Project.trading.model.User;
import com.Project.trading.model.VerificationCode;
import com.Project.trading.request.ForgotPasswordTokenRequest;
import com.Project.trading.request.ResetPasswordRequest;
import com.Project.trading.response.ApiResponse;
import com.Project.trading.response.AuthResponse;
import com.Project.trading.service.EmailService;
import com.Project.trading.service.UserService;
import com.Project.trading.service.VerificationCodeService;
import com.Project.trading.serviceInterface.ForgotPasswordService;
import com.Project.trading.utils.OtpUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("verification/{verification}/send-otp")
	public ResponseEntity<String> sendVerification(@RequestHeader("Authorization") String jwt, 
			@PathVariable VerificationType verificationType) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		
		if(verificationCode == null) {
			verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
		}
		if(verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}
		
		return new ResponseEntity<>("Verification otp sent successfully.", HttpStatus.OK);
	}
	
	@GetMapping("verification/{verification}/verify-otp")
	public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt, 
			@PathVariable String otp) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? 
				verificationCode.getEmail() : verificationCode.getMobile();
		
		boolean isVerified = verificationCode.getOtp().equals(otp);
		
		if(isVerified) {
			User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), 
					sendTo, user);
			verificationCodeService.deleteVerificationCodeById(verificationCode);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
		
		throw new Exception("Wrong OTP");
	}
	
	@GetMapping("reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOtp( 
			@RequestBody ForgotPasswordTokenRequest request) throws Exception{
		
		User user = userService.findUserByEmail(request.getSendTo());
		String otp = OtpUtils.generateOtp();
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		
		ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
		
		if(token == null) {
			token = forgotPasswordService.createToken(user, id, otp, request.getVerificationType(), request.getSendTo());
		}
		
		if(request.getVerificationType().equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}
		
		AuthResponse response = new AuthResponse();
		response.setSession(token.getId());
		response.setMessage("Password reset otp sent successfully");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("reset-password/reset-otp")
	public ResponseEntity<ApiResponse> resetPassword(@RequestHeader("Authorization") String jwt, 
			@RequestParam String id,
			@RequestBody ResetPasswordRequest request) throws Exception{
		//User user = userService.findUserProfileByJwt(jwt);
		
		ForgotPasswordToken token = forgotPasswordService.findById(id);
		boolean isVerified = token.getOtp().equals(request.getOtp());
		
		if(isVerified) {
			userService.updatePassword(token.getUser(), request.getPassword());
			ApiResponse response = new ApiResponse();
			response.setMessage("Password updated successfully");
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}
		
		throw new Exception("Wrong OTP");
	}
}

















