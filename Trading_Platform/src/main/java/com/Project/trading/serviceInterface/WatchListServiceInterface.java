package com.Project.trading.serviceInterface;

import com.Project.trading.model.Coin;
import com.Project.trading.model.User;
import com.Project.trading.model.WatchList;

public interface WatchListServiceInterface {
	WatchList findUserWatchList(Long userId) throws Exception;
	
	WatchList createWatchList(User user);
	
	WatchList findById(Long id) throws Exception;
	
	Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
