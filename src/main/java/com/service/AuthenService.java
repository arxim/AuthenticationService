package com.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.model.User;
import com.util.MD5;

@Service
public class AuthenService {

	@Autowired
	private UserDao userDao;
	
	public User user_verify(String email, String password, String company, String role) {
		
		User user = new User();
		
		try {
			String en_password = MD5.encrypt(password);
			user = userDao.user_verify(email, en_password, company, role);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
}