package com.example.TesinaProjectBank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.MyUserDetails;
import com.example.TesinaProjectBank.models.User;
import com.example.TesinaProjectBank.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user= Optional.ofNullable(userRepository.findByUsername(username));
		
		user.orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado " + username));
		
		return user.map(MyUserDetails::new).get();
		
	}
	
	
}
