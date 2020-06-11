package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.LoginDao;
import com.service.DropdownServices;

import org.json.JSONObject;

@WebServlet("/DropDownSrvl")
public class DropDownSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		LoginDao dao = new LoginDao();
		DropdownServices service = new DropdownServices();
		String email = null;
        String company = request.getParameter("company");
        String key = request.getParameter("key");
        
        if (request.getParameter("email_encrypt") != null) {
        	System.out.println(request.getParameter("email_encrypt"));
        	// Getting decoder  
            Base64.Decoder decoder = Base64.getDecoder();
            // Decoding string  
            email = new String(decoder.decode(request.getParameter("email_encrypt")));  
            System.out.println("Decoded email: "+email);
		} else {
			email = request.getParameter("email");
		}
        
        JSONObject company_ = new JSONObject();
        JSONObject company_from_email = new JSONObject();
        List<Map<String, String>> role_ = new ArrayList<Map<String, String>>();
        JSONObject role_from_email = new JSONObject();
        
        String dwRole = null;
        PrintWriter out = response.getWriter();
        
        switch(key) {
        case "getCompany" : company_ = dao.getCompany();
        					out.println(company_);
        					break;
        case "getRole" : role_ = dao.getRole(company);
        				 dwRole = service.addDropdown(role_);
        				 out.println(dwRole);
						 break;
        case "getCompanyByEmail" : company_from_email = dao.getCompanyByEmail(email);
        						   out.println(company_from_email);
        						   break;
        case "getRoleByEmail" : role_from_email = dao.getRoleByEmail(email,company);
							    out.println(role_from_email);
							    break;
        }
	}
}