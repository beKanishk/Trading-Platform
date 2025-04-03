package com.Project.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.trading.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>{
	List<Asset> findByUserId(Long useId);
	
	Asset findByUserIdAndCoinId(Long userId, String coinId);
}
