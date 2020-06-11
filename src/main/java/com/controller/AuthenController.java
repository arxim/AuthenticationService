package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
		//System.out.println("Post Method <> email : " + email + ", password : " +  password+", company : " + company + ", role : " +  role);
		return ""+authenService.verifyUser(email, password, company, role);		
	}
	
	//Get method for Test
	@RequestMapping(value = "/user_verify/{email}/{password}/{company}/{role}", method = RequestMethod.GET)
	public String user_verify_get(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("company") String company, @PathVariable("role") String role, HttpServletRequest request) {
		System.out.println("Get Method <|> email : " + email + ", password : " +  password+", company : " + company + ", role : " +  role);
		//return ""+authenService.verifyUser(email, password, company, role);
		if(authenService.verifyUser(email, password, company, role)){
			return "pages/NewFile";
		}else{
			return "pages/FailPage";
		}
	}
	//end test
	
	//Get method for Test
	@RequestMapping(value = "/ext_forward", method = RequestMethod.GET)
	public ModelAndView user_verify_get() {
		System.out.println("Get Method <|> email : ");
	    return new ModelAndView("redirect:" + "https://www.google.com/test");
		//RedirectView a = new RedirectView();
		//a.setUrl("https://www.google.com/test");
		//return a;
	}
	//end test

	//Get method for Test Thymeleaf
	@RequestMapping(value = "/thymeleaf", method = RequestMethod.GET)
	public String user_verify_get2(ModelMap model) {
		System.out.println("Get Method <|> email : ");
	    String hello = "Hello Pattarachai!";
	    model.addAttribute("testHello",hello);
	    
	    User usr = new User();
	    usr.setFirstname("Nopphadon");
	    usr.setLastname("Chansi");
	    model.addAttribute("user",usr);
		return "pages/NewFile";
	}
	//end test
}
