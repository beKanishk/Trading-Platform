package com.Project.trading.request;


import com.Project.trading.domain.VerificationType;

public class ForgotPasswordTokenRequest {
	private String sendTo;
	private VerificationType verificationType;
	
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public VerificationType getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(VerificationType verificationType) {
		this.verificationType = verificationType;
	}
}
