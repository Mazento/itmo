package com.practice;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Pract32Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));
		int c = Integer.parseInt(req.getParameter("c"));
		int d;
		double x1, x2;
		String result;
		d = b * b - 4 * a * c;
		if (d > 0) {
			x1 = (-b + Math.sqrt(d)) / (2 * a);
			x2 = (-b - Math.sqrt(d)) / (2 * a);
			result = "x1 = " + Double.toString(x1) + ", x2 = " + Double.toString(x2);
			
		}
		else if (d == 0) {
			x1 = -b / (2 * a);
			result = "x = " + Double.toString(x1);
		}
		else
			result = "No roots";
		resp.getWriter().println(result);
	}
}
