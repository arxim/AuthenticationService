package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.model.User;
import com.service.AuthenService;

@Controller
@CrossOrigin
@RequestMapping(value = "/authen")
@SessionAttributes("_user")
public class AuthenController {

	@Autowired
	private AuthenService authenService;
	
	@RequestMapping(value = "/user_verify", method = RequestMethod.POST)
	public @ResponseBody String user_verify(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("company_login") String company, @RequestParam("role_login") String role, HttpServletRequest request) {
		
		System.out.println("email : " + email + " ,password : " +  password);
		System.out.println("company : " + company + " ,role : " +  role);
		return ""+authenService.verifyUser(email, password, company, role);		
	}
	
	//Test get method
	@RequestMapping(value = "/user_verify/{email}/{password}/{role}/{company}", method = RequestMethod.GET)
	public @ResponseBody String user_verify_get(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("company") String company, @PathVariable("role") String role, HttpServletRequest request) {
		System.out.println("email : " + email + " ,password : " +  password);
		System.out.println("company : " + company + " ,role : " +  role);
		return ""+authenService.verifyUser(email, password, company, role);		
	}
	//end test
}
