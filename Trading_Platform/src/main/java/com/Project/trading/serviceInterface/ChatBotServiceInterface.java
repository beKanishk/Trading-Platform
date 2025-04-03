package com.Project.trading.serviceInterface;

import com.Project.trading.response.ApiResponse;

public interface ChatBotServiceInterface {
	ApiResponse getCoinDetails(String prompt) throws Exception;
	
	String simpleChat(String prompt);
}
