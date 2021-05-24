package Delegates;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.ReimbUsers;
import Service.EmployeeService;
import Service.Employee_Service_Defined;
import Utility.BusinessException;

public class loginDelegate implements Delegatable{
	private EmployeeService es = new Employee_Service_Defined();
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		PrintWriter pw = response.getWriter();
		
		switch(request.getMethod()) {
		case "POST":
			String userInputUsername = request.getParameter("username");
			String userInputPassword = request.getParameter("password");
			
			try {
				if(es.loginCheck(userInputUsername, userInputPassword) == true){
					response.setStatus(201);
					Cookie cookie = new Cookie("user",request.getParameter("username"));
					response.addCookie(cookie);
					if(es.getUser(userInputUsername).getRoles().getUserRole().equals("employee")) {
						response.sendRedirect("static/employee.html");
					}if(es.getUser(userInputUsername).getRoles().getUserRole().equals("manager")) {
						response.sendRedirect("static/manager.html");
					}
				}else {
					String loginFailed ="<div>Please try harder to remember your credentials</div>";
					
					pw.print(loginFailed);
					
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	

}
