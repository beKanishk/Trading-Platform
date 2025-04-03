package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long>{

}
