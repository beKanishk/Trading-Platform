package com.Project.trading.serviceInterface;

import java.util.List;

import com.Project.trading.domain.OrderType;
import com.Project.trading.model.Coin;
import com.Project.trading.model.Order;
import com.Project.trading.model.OrderItem;
import com.Project.trading.model.User;

public interface OrderServiceInterface {
	Order createOrder(User user, OrderItem orderItem, OrderType orderType);
	
	Order getOrderById(Long orderId) throws Exception;
	
	List<Order> getAllOrderOfUser(Long userId,OrderType orderType, String assetSymbol);
	
	Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
