package com.Project.trading.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.domain.OrderStatus;
import com.Project.trading.domain.OrderType;
import com.Project.trading.model.Asset;
import com.Project.trading.model.Coin;
import com.Project.trading.model.Order;
import com.Project.trading.model.OrderItem;
import com.Project.trading.model.User;
import com.Project.trading.repository.OrderItemRepository;
import com.Project.trading.repository.OrderRepository;
import com.Project.trading.serviceInterface.OrderServiceInterface;

import jakarta.persistence.Id;
import jakarta.transaction.Transactional;

@Service
public class OrderService implements OrderServiceInterface{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AssetService assetService;
	
	@Override
	public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
		double amount = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
		
		Order order = new Order();
		order.setUser(user);
		order.setAmount(BigDecimal.valueOf(amount));
		order.setOrderType(orderType);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setTimeStamp(LocalDateTime.now());
		order.setOrderItem(orderItem);
		
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {
		return orderRepository.findById(orderId).orElseThrow(() -> new Exception("order not found"));
	}

	@Override
	public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
		return orderRepository.findByUserId(userId);
	}
	
	private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
		OrderItem orderItem = new OrderItem();
		orderItem.setCoin(coin);
		orderItem.setBuyPrice(buyPrice);
		orderItem.setSellPrice(sellPrice);
		orderItem.setQuantity(quantity);
		
		return orderItemRepository.save(orderItem);
	}
	
	@Transactional
	public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
		if(quantity <= 0) {
			throw new Exception("Quantity should be greater than 0");
		}
		
		double buyPrice = coin.getCurrentPrice();
		OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
		Order order = createOrder(user, orderItem, OrderType.BUY);
		orderItem.setOrder(order);
		
		walletService.payOrderPayment(order, user);
		
		order.setOrderStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.BUY);
		Order savedOrder = orderRepository.save(order);
		
		//create asset
		Asset oldAsset = assetService.getAssetByUserIdAndCoinId(order.getUser().getId(), 
				order.getOrderItem().getCoin().getId());
		
		if(oldAsset == null) {
			assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
		}
		else {
			assetService.updateAsset(oldAsset.getId(), quantity);
		}
		
		return savedOrder;
	}
	
	@Transactional
	public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
	    if (quantity <= 0) {
	        throw new Exception("Quantity should be greater than 0");
	    }

	    double sellPrice = coin.getCurrentPrice();

	    Asset assetToSell = assetService.getAssetByUserIdAndCoinId(user.getId(), coin.getId());
	    
	    if (assetToSell == null) {
	        throw new Exception("Asset not found for userId: " + user.getId() + " and coinId: " + coin.getId());
	    }

	    double buyPrice = assetToSell.getBuyPrice(); // Now safe to access

	    if (assetToSell.getQuantity() >= quantity) {
	        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
	        Order order = createOrder(user, orderItem, OrderType.SELL);
	        orderItem.setOrder(order);
	        order.setOrderStatus(OrderStatus.SUCCESS);

	        Order savedOrder = orderRepository.save(order);

	        walletService.payOrderPayment(order, user);

	        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
	        if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
	            assetService.deleteAsset(updatedAsset.getId());
	        }

	        return savedOrder;
	    }

	    throw new Exception("Insufficient quantity to sell");
	}
	
	@Transactional
	@Override
	public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
		if(orderType.equals(OrderType.BUY)) {
			return buyAsset(coin, quantity, user);
		}
		else if(orderType.equals(OrderType.SELL)) {
			return sellAsset(coin, quantity, user);
		}
		
		throw new Exception("invalid order type");
	}

}
