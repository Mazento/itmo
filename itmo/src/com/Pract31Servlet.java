package com;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Pract31Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		double m = Double.parseDouble(req.getParameter("m"));
		double n = Double.parseDouble(req.getParameter("n"));
		double result = Math.abs(m - 10) < Math.abs(n - 10) ? m : n;
		resp.getWriter().println(Double.toString(result));
	}
}
