package com.bookshelf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;
import java.io.InputStreamReader;

import javax.servlet.http.*;

import org.datanucleus.store.types.sco.simple.Map;

@SuppressWarnings("serial")
public class BookshelfServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain; charset=utf-8");
		String command = req.getParameter("command");
		String result = "";
//		String page = req.getParameter("page");
//		result = getTemplate("navigation.html");
		
		switch (command) {
			case "navigation": {
				String page = req.getParameter("page");
				result = makeNavigation(page);
				break;
			}
			case "loadbook": {
				String book = req.getParameter("book");
				result = makeBook(book);
				break;
			}
		}
		
		resp.getWriter().println(result);
	}
	
	private String getTemplate(String templateName) {
		String template = "";
		String line = "";
		try {
			File fileDir = new File("templates/" + templateName);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			while ((line = in.readLine()) != null) {
				template += line;
			}
			in.close();
		} catch (Exception e) {
			template = e.getMessage();
		}
		return template;
	}
	
	private String makeNavigation(String page) {
		String template = getTemplate("navigation.html");
		for (int i=1; i<=5; i++) {
			if (i == Integer.parseInt(page))
				template = substitute(template, "active" + i, "active");
			else
				template = substitute(template, "active" + i, "");
		}
		return template;
	}
	
	private String makeBook(String book) {
		String template = getTemplate("book.html");
		HashMap<String, String> bookctx = parseBookRecord(book);
		for(Map.Entry<String, String> entry : bookctx.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue().toString();
			template = substitute(template, key, value);
		}
		return template;
	}
	
	private String substitute(String template, String reg, String repl) {
		String result;
		result = template.replaceAll("\\$\\(" + reg + "\\)", repl);
		return result;
	}
	
	private HashMap parseBookRecord(String recordname) {
		String line = "";
		HashMap ctx = new HashMap();
		try {
			File fileDir = new File("records/books/" + recordname);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			while ((line = in.readLine()) != null) {
				ctx.put(line.split("=", 2)[0], line.split("=", 2)[1]);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctx;
	}
}
