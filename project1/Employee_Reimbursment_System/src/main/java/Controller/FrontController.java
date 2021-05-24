package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import Service.EmployeeService;
import Service.Employee_Service_Defined;
import Utility.BusinessException;


public class FrontController extends DefaultServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestHelper rh = new RequestHelper();
	Logger log = Logger.getLogger(FrontController.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String path = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("what my context path "+request.getContextPath());
		System.out.println("what my uri "+request.getRequestURI());
		System.out.println("what my url "+ request.getRequestURI());
		System.out.println(path);
		if(path.startsWith("/static/") || path.equals("/") || path.equals("/index.html") ) {
			super.doGet(request, response);
		}else {
		
			rh.RequestDirector(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}
	
}
