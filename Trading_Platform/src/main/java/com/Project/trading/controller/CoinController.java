package com.Project.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.model.Coin;
import com.Project.trading.service.CoinService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("coins")
public class CoinController {
	@Autowired
	private CoinService coinService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@GetMapping
	ResponseEntity<List<Coin>> getCoinList(@RequestParam int page) throws Exception{
		List<Coin> coins = coinService.getCoinList(page);
		return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("{coinId}/chart")
	ResponseEntity<JsonNode> getMarketChart(@RequestParam int days, @PathVariable String coinId) throws Exception{
		String response = coinService.getMarketChart(coinId, days);
		
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("search")
	ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception{
		String response = coinService.searchCoin(keyword);
		
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("top50")
	ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank() throws Exception{
		String response = coinService.getTop50CoinsByMarketCapRank();
		
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("trending")
	ResponseEntity<JsonNode> getTrendingCoin() throws Exception{
		String response = coinService.getTrendingCoins();
		
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("details/{coinId}")
	ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception{
		String response = coinService.getCoinDetails(coinId);
		
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
}
