package Delegates;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import Service.EmployeeService;
import Service.Employee_Service_Defined;
import Utility.BusinessException;

@WebServlet("/reimbrequest")
public class Reimbursement extends HttpServlet{
	Logger log = Logger.getLogger(Reimbursement.class);
	private EmployeeService es = new Employee_Service_Defined();
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		String reimbType = request.getParameter("typeOfEvent");
		System.out.println(reimbType);
		Double cost = Double.parseDouble(request.getParameter("cost"));
		System.out.println(cost);
		String descript = request.getParameter("description");
		System.out.println(descript);
		Cookie employeeCookie[] = request.getCookies();
		System.out.println(employeeCookie[0].getValue());
		
		try {
			es.submitReimb(reimbType, cost, descript, es.getUser(employeeCookie[0].getValue()).getId());
		} catch (BusinessException e) {
			log.info("Reimbursement delegate failed");
			e.printStackTrace();
		}
	}
	

}
