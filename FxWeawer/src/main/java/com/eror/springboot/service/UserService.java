package com.eror.springboot.service;


import com.eror.springboot.generic.GenericService;
import com.eror.springboot.model.User;

public interface UserService extends GenericService<User> {

	boolean authenticate(String email, String password);
	
	User findByEmail(String email);
	
}
