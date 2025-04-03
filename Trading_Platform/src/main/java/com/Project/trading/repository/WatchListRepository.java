package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.trading.model.WatchList;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long>{
	WatchList findByUserId(Long userId);
}
