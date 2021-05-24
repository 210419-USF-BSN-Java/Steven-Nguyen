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
import Service.ManagerService;
import Service.Manager_Service_Defined;
import Utility.BusinessException;

public class ManagerDelegate implements Delegatable {
	private ManagerService ms = new Manager_Service_Defined();
	private EmployeeService es = new Employee_Service_Defined();
	private ObjectMapper om = new ObjectMapper();

	private int id = 0;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = (String) request.getAttribute("path");
		Cookie cookies[] = request.getCookies();
		PrintWriter pw = response.getWriter();

		try {
			id = es.getUser(cookies[0].getValue()).getId();
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		switch (request.getMethod()) {
		case "GET":
			switch (path) {
			case "viewPending":
				try {
					List<Reimb> pending = ms.getReimbursementList();
					response.getWriter().write(om.writeValueAsString(pending));
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "resolved":
				try {
					List<Reimb> resolvedReimb = ms.viewAllResolved();
					response.getWriter().write(om.writeValueAsString(resolvedReimb));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "employees":
				try {
					List<ReimbUsers> users = ms.viewAllEmployees();
					response.getWriter().write(om.writeValueAsString(users));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

				
			}

		case "POST":
			switch (path) {
			case ("decide"):
				int reimbId = Integer.parseInt(request.getParameter("approve"));
				try {
					if (ms.decide(reimbId, id) == true) {
						pw.print((String) "<div>Reimbursement approved successfully recieved</div>");
						pw.print((String) "<button type=\"button\"><a href='http://localhost:8080/ERS/static/manager.html'>Go Back</a></button>");
						
					} else {
						pw.print((String) "<div>Reimbursement approved ERROR</div>");
						pw.print((String) "<button type=\"button\"><a href='http://localhost:8080/ERS/static/manager.html'>Go Back</a></button>");
					}
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "info":
				try {

					int userId = Integer.parseInt(request.getParameter("eId"));

					List<Reimb> user = ms.viewReimbByEmployeeId(userId);

					response.getWriter().write(om.writeValueAsString(user));
					

				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		
		

		}

	}
}
