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
	
	public boolean verifyUser(String email, String password, String company, String role) {
		boolean status = true;
		try {
			String en_password = MD5.encrypt(password);
			status = userDao.verifyUser(email, en_password, company, role);
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}
}