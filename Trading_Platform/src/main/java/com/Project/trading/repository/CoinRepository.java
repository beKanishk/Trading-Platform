package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.trading.model.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String>{
	
}
