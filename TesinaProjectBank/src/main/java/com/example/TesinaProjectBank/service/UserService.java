package com.example.TesinaProjectBank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.User;
import com.example.TesinaProjectBank.models.UserData;
import com.example.TesinaProjectBank.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserData userToUserData(User user) {
		UserData userData=new UserData();
		userData.setId(user.getId());
		userData.setLastname(user.getLastname());
		userData.setMail(user.getMail());
		userData.setName(user.getName());
		return userData;
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public User getUserByName(User user) {
		return userRepository.findByUsername(user.getUsername());
	}
}
