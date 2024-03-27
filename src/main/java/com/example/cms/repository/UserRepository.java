package com.example.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cms.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

	@Query("FROM User user WHERE user.email=:email AND user.password=:password ")
	User existByPassword(String email, String password);
}
