package com;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Pract33Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		int[][] arr1 = new int[8][5];
		Random rng = new Random();
		String result = "";
		for (int i = 0; i < arr1.length; i++)
			for (int j = 0; j < arr1[i].length; j++)
				arr1[i][j] = rng.nextInt(90) + 10;
		
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++)
				result += String.format("%3d", arr1[i][j]);
			result += "<br>";
		}
		resp.getWriter().println(result);
	}
}
