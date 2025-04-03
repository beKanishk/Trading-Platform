package com.Project.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.trading.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String username);

}
