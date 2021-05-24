package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Delegates.Delegatable;
import Delegates.EmployeeDelegate;
import Delegates.ManagerDelegate;
import Delegates.Reimbursement;
import Delegates.loginDelegate;


public class RequestHelper {
	Delegatable ed = new EmployeeDelegate();
	Delegatable ld = new loginDelegate();
	Delegatable md = new ManagerDelegate();
	

	public void RequestDirector(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		StringBuilder buildingUri = new StringBuilder(request.getRequestURI());

		buildingUri.replace(0, request.getContextPath().length() + 1, "");
		
		if (buildingUri.indexOf("/") != -1) {
			request.setAttribute("path", buildingUri.substring(buildingUri.indexOf("/") + 1));

			//System.out.println(buildingUri);

			buildingUri.replace(buildingUri.indexOf("/"), buildingUri.length(), "");
			//System.out.println(buildingUri);

		}
		
		switch (buildingUri.toString()) {
		case "login":
			ld.process(request, response);
			break;

		case "employee":
			ed.process(request, response);
			break;

		case "manager":
			md.process(request, response);
			break;

		default:
			response.sendError(404, "Path not supported.");
			break;
		
		}
		
		
	}

}
