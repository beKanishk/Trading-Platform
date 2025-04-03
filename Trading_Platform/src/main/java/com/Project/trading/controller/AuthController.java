package com.Project.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.configuration.JwtProvider;
import com.Project.trading.model.TwoFactorOTPModel;
import com.Project.trading.model.User;
import com.Project.trading.repository.UserRepository;
import com.Project.trading.response.AuthResponse;
import com.Project.trading.service.CustomerUserDetailsService;
import com.Project.trading.service.EmailService;
import com.Project.trading.service.TwoFactorOTPService;
import com.Project.trading.service.WatchListService;
import com.Project.trading.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TwoFactorOTPService twoFactorOTPService;

	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private WatchListService watchListService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {
		User isEmailExist = userRepository.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new Exception("email is already in use with another account.");
		}

		User newUser = new User();

		newUser.setName(user.getName());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
	
		User savedUser = userRepository.save(newUser);
		
		watchListService.createWatchList(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = JwtProvider.generateToken(authentication);

		AuthResponse response = new AuthResponse();

		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("register success");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody User user) throws Exception {
		String userEmail = user.getEmail();
		String userPassword = user.getPassword();

		Authentication authentication = authenticate(userEmail, userPassword);
		User authUser = userRepository.findByEmail(userEmail);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = JwtProvider.generateToken(authentication);

		if (user.getTwoFactorAuth().isEnabled()) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two factor authentication is enabled.");
			response.setTwoFactorAuthEnabled(true);
			String otp = OtpUtils.generateOtp();

			TwoFactorOTPModel oldTwoFactorOTPModel = twoFactorOTPService.findByUser(authUser.getId());

			if (oldTwoFactorOTPModel != null) {
				twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOTPModel);
			}

			TwoFactorOTPModel newTwoFactorOTPModel = twoFactorOTPService.crateTwoFactorOTP(authUser, otp, jwt);
			
			emailService.sendVerificationOtpEmail(userEmail, otp);
			
			response.setSession(newTwoFactorOTPModel.getId());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		AuthResponse response = new AuthResponse();

		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("login success");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	private Authentication authenticate(String userEmail, String userPassword) {
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userEmail);

		if (userDetails == null) {
			throw new BadCredentialsException("inavlid username");
		}

		if (!userPassword.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}

//		return new UsernamePasswordAuthenticationToken(userPassword, userDetails, userDetails.getAuthorities());
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

	}
	
	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySigninOtp(@PathVariable String otp, @RequestParam String id) throws Exception{
		TwoFactorOTPModel twoFactorOTPModel = twoFactorOTPService.findById(id);
		
		if(twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTPModel, otp)) {
			AuthResponse response = new AuthResponse();
			
			response.setMessage("Two factor authentication varified");
			response.setTwoFactorAuthEnabled(true);
			response.setJwt(twoFactorOTPModel.getJwt());
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new Exception("invalid otp");
	}
}
















