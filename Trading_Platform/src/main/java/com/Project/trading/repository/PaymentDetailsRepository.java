package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.trading.model.PaymentDetails;


@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>{
	PaymentDetails findByUserId(Long userId);
}
