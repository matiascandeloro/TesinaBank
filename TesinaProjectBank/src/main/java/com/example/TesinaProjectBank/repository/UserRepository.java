package com.example.TesinaProjectBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TesinaProjectBank.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	User findById(int id);
	User save(User user);
	void delete(User user);
}
