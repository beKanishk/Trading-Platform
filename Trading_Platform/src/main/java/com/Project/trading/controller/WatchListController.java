package com.Project.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.Project.trading.model.Coin;
import com.Project.trading.model.User;
import com.Project.trading.model.WatchList;
import com.Project.trading.service.CoinService;
import com.Project.trading.service.UserService;
import com.Project.trading.service.WatchListService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
	@Autowired
	private WatchListService watchListService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		WatchList watchList = watchListService.findUserWatchList(user.getId());
		return ResponseEntity.ok(watchList);
	}
	
//	@PostMapping("/create")
//	public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception{
//		User user = userService.findUserProfileByJwt(jwt);
//		WatchList watchList = watchListService.createWatchList(user);
//		return ResponseEntity.status(HttpStatus.CREATED).body(watchList);
//	}
	
	
	@GetMapping("/{watchlistId}")
	public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchlistId) throws Exception{
		WatchList watchlist = watchListService.findById(watchlistId);
		return ResponseEntity.ok(watchlist);
	}
	
	
	@PatchMapping("/add/coin/{coinId}")
	public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt, 
			@PathVariable String coinId) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(coinId);
		Coin addedCoin = watchListService.addItemToWatchList(coin, user);
		
		return ResponseEntity.ok(addedCoin);
	}
}
