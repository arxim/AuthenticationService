package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
//import login.services.SendMailServices;

/**
 * Servlet implementation class ActivateSrvl
 */
@WebServlet("/ActivateSrvl")
public class ActivateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActivateController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		String param3 = request.getParameter("param3");
		
		System.out.println("param 1 >> " + param1);
		System.out.println("param 2 >> " + param2);
		System.out.println("param 3 >> " + param3);
		
		 // Getting decoder  
        Base64.Decoder decoder = Base64.getDecoder();
        // Decoding string  
        String email_decode = new String(decoder.decode(param1));  
        System.out.println("Decoded email: "+email_decode);  
        
        String companycode_decode = new String(decoder.decode(param2));  
        System.out.println("Decoded email: "+companycode_decode);
        
        String rolecode_decode = new String(decoder.decode(param3));  
        System.out.println("Decoded email: "+rolecode_decode);
        
        UserDao userDao = new UserDao();
        boolean result = userDao.activateUser(email_decode, companycode_decode, rolecode_decode);
        if (result) {
			System.out.println("Update success");
			
			//SendMailServices sm = new SendMailServices();
			//sm.sendToUser(email_decode, companycode_decode, rolecode_decode);
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1 align='center'>Activated</h1>");
		} else {
			System.out.println("Update fail");
		}
	}

}
