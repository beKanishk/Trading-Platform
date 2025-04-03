package com.Project.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.trading.domain.OrderType;
import com.Project.trading.model.Coin;
import com.Project.trading.model.Order;
import com.Project.trading.model.User;
import com.Project.trading.request.CreateOrderRequest;
import com.Project.trading.service.CoinService;
import com.Project.trading.service.OrderService;
import com.Project.trading.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@PostMapping("/pay")
	public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,
			@RequestBody CreateOrderRequest request) throws Exception{
		
		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(request.getCoinId());
		
		Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(), user);
		
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderID) throws Exception{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.getOrderById(orderID);
		if(order.getUser().getId().equals(user.getId())) {
			return ResponseEntity.ok(order);
		}
		
		throw new Exception("Invalid User");
	}
	
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrdersOfUser(@RequestHeader("Authorization") String jwt,
			@RequestParam(required = false) OrderType orderType,
			@RequestParam(required = false) String assetSymbol) throws Exception{
		
		Long userId = userService.findUserProfileByJwt(jwt).getId();
		
		List<Order> userOrders = orderService.getAllOrderOfUser(userId, orderType, assetSymbol);
		
		return ResponseEntity.ok(userOrders);
	}
	
}
