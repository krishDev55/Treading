package com.trade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.modal.User;
import com.trade.repository.UserRepository;

@Service
public class UserService {
		
	@Autowired 
		UserRepository urRepo;
	
	public User getUserByEmail(String email) {
		System.out.println("inside email method >>>>>>>>>>>>>>>>>>>>>>>>");
		List<User> all = urRepo.findAll();
		System.out.println("after is ::::::::::::: "+all);
		User user=all.stream().filter(f->f.getEmail().equals(email)).findFirst().get();
		return  user;
	}
}
