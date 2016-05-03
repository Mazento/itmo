package com;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Pract34Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		int[][] arr1 = new int[8][5];
		int maxVal;
		Random rng = new Random();
		String result = "";
		
		maxVal = 0;
		for (int i = 0; i < arr1.length; i++)
			for (int j = 0; j < arr1[i].length; j++) {
				arr1[i][j] = rng.nextInt(199) - 99;
				if (arr1[i][j] > maxVal)
					maxVal = arr1[i][j];
			}
		
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++)
				result += String.format("%4d", arr1[i][j]).replaceAll(" ", "&nbsp;");
			result += "<br>";
		}
		result += "Maximum value: " + Integer.toString(maxVal);
		resp.getWriter().println(result);
	}
}
