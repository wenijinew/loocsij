package org.loocsij.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest res, HttpServletResponse rep)
			throws ServletException, IOException {
		rep.setContentType("text/html");
		rep.getWriter().print("Hello World!");
	}

}
