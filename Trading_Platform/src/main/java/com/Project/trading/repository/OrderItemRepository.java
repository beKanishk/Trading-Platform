package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
