package Delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Reimb;
import Model.ReimbUsers;
import Service.EmployeeService;
import Service.Employee_Service_Defined;
import Utility.BusinessException;

public class EmployeeDelegate implements Delegatable{
	private EmployeeService es = new Employee_Service_Defined();
	private ObjectMapper om = new ObjectMapper();
	private int id = 0;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String path = (String) request.getAttribute("path");
		
		Cookie cookies[]=request.getCookies();
		
		try {
			id = es.getUser(cookies[0].getValue()).getId();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		switch(request.getMethod()){
		case "GET":
			switch(path) {
			case"pending":
				
				try {
					List<Reimb> pendingList = es.viewPending(id);
					response.getWriter().write(om.writeValueAsString(pendingList));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				break;
			case"resolved":
				try {
					List<Reimb> resolvedList = es.viewResolved(id);
					response.getWriter().write(om.writeValueAsString(resolvedList));
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case"info":
				try {
					ReimbUsers userInfo = es.getUser(cookies[0].getValue());
					response.getWriter().write(om.writeValueAsString(userInfo));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
			break;
			
		case "POST":
			PrintWriter pw = response.getWriter();
			double amount = 0;
		
			if(request.getParameter("cost")=="") {
				
			}else {
				amount = Double.parseDouble(request.getParameter("cost"));
			}
			
			String type = request.getParameter("typeOfEvent");
			String descript = request.getParameter("description");
			
			try {
				
				if(request.getParameter("cost").equals(null)){
					pw.print((String) "<div>Request has failed</div>");
				}
				if(es.submitReimb(type, amount, descript, id)==true) {
				pw.print((String) "<div>Reimbursement request successfully recieved</div>");	
				pw.print((String) "<button type=\"button\"><a href='http://localhost:8080/ERS/static/employee.html'>Return to Employee Homepage</a></button>");
				}else {
					pw.print((String) "<div>Request has failed</div>");
					pw.print((String) "<button type=\"button\"><a href='http://localhost:8080/ERS/static/employee.html'>Go Back</a></button>");
				}
		
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	
	}
	
}
