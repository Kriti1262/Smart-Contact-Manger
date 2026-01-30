package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SmartService {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bycryptpasswordencoder;
	public String registerUser(User user) {
		// TODO Auto-generated method stub
		if(userRepository.existsByEmail(user.getEmail())) {
			System.out.print("Email already registered");
			return "Email already registered";
		}
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		user.setPassword(bycryptpasswordencoder.encode(user.getPassword()));
		 user.setImageUrl("default.png");
		userRepository.save(user);
		System.out.print("User registered successfully");
		return "User registered successfully";
	}

}
