package com.Project.trading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.model.Coin;
import com.Project.trading.model.User;
import com.Project.trading.model.WatchList;
import com.Project.trading.repository.WatchListRepository;
import com.Project.trading.serviceInterface.WatchListServiceInterface;

@Service
public class WatchListService implements WatchListServiceInterface{

	@Autowired
	private WatchListRepository watchListRepository;
	
	@Override
	public WatchList findUserWatchList(Long userId) throws Exception {
		WatchList watchList = watchListRepository.findByUserId(userId);
		
		if(watchList == null) {
			throw new Exception("watchlist not found");
		}
		return watchList;
	}

	@Override
	public WatchList createWatchList(User user) {
		WatchList watchList = new WatchList();
		watchList.setUser(user);
		return watchListRepository.save(watchList);
	}

	@Override
	public WatchList findById(Long id) throws Exception {
		Optional<WatchList> watchlistOptional = watchListRepository.findById(id);
		
		if(watchlistOptional.isEmpty()) {
			throw new Exception("watchlist not found");
		}
		return watchlistOptional.get();
	}

	@Override
	public Coin addItemToWatchList(Coin coin, User user) throws Exception {
		WatchList watchList = findUserWatchList(user.getId());
		
		if(watchList.getCoins().contains(coin)) {
			watchList.getCoins().remove(coin);
		}
		else {
			watchList.getCoins().add(coin);
		}

		watchListRepository.save(watchList);
		return coin;
	}

}
