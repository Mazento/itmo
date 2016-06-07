package com.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.*;
import com.csv.HotelsTable;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CsvServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		resp.setContentType("text/plain; charset=utf-8");
		String request = req.getParameter("request");
		String filter = req.getParameter("filter");
		String order = req.getParameter("order");
		String districtFilter = req.getParameter("district");
		String template = "";
		if (request != null && request.equals("districtList"))
			resp.getWriter().println(getDistrictsList());
		else {
			template = getTemplate("csv_table.html");
			if (order.equals("asc"))
				template = substitute(template, "caret", "up");
			else
				template = substitute(template, "caret", "down");
			template = substitute(template, "csv_table", makeRows(filter, order, districtFilter));
			resp.getWriter().println(template);
		}
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

private String makeRows(String filter, String order, String districtFilter) throws IOException {
		String result = "";
		String template = getTemplate("csv_list_item.html");
		String tmp = "";
		try {
			String [] nextLine;		

			CSVReader readerDistr = new CSVReader(new InputStreamReader(new FileInputStream("districts.csv"), "UTF8"));
			List<DistrictsTable> districts = new ArrayList<DistrictsTable>();
			nextLine = readerDistr.readNext();
			while ((nextLine = readerDistr.readNext()) != null) {
				DistrictsTable district = new DistrictsTable();
				district.setDistrict(nextLine[0]);
				district.setDistrictCode(nextLine[2]);
				districts.add(district);
			}
			readerDistr.close();
			
			CSVReader readerDistr2 = new CSVReader(new InputStreamReader(new FileInputStream("hotels.csv"), "UTF8"));
			List<HotelsTable> hotels = new ArrayList<HotelsTable>();
			nextLine = readerDistr2.readNext();
			while ((nextLine = readerDistr2.readNext()) != null) {
				if (nextLine[1].matches("(.*)(?iu)" + filter + "(.*)") == true)
					if (districtFilter.equals("none") == true || nextLine[8].split(" ")[0].equals(districtFilter)) {
						HotelsTable hotel = new HotelsTable();
						hotel.setName(nextLine[1]);
						hotel.setHclass(nextLine[2]);
						if (nextLine[3].equals("") == false)
							hotel.setRooms(Integer.parseInt(nextLine[3]));
						else
						 	hotel.setRooms(0);
						hotel.setAddress(nextLine[6]);
						hotel.setDistrict(nextLine[8].split(" ")[0]);
						for (DistrictsTable distr: districts) {
							if (distr.getDistrict().equals(nextLine[8].split(" ")[0])) {
								hotel.setDistrictCode(distr.getDistrictCode());
								break;
							}
					}
					hotel.setMetro(nextLine[12]);
					hotels.add(hotel);
				}
			}
			readerDistr2.close();

			hotels = sortTable(hotels, order);

			for (HotelsTable hotel: hotels) {
				tmp = template;
				tmp = substitute(tmp, "name", hotel.getName());
				tmp = substitute(tmp, "class", hotel.getHclass());
				tmp = substitute(tmp, "rooms", hotel.getRooms().toString());
				tmp = substitute(tmp, "address", hotel.getAddress());
				tmp = substitute(tmp, "district", hotel.getDistrict());
				tmp = substitute(tmp, "discrict_code", hotel.getDistrictCode());
				tmp = substitute(tmp, "metro", hotel.getMetro());
				result += tmp;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<HotelsTable> sortTable(List<HotelsTable> hotels, final String order) {
		if (order != "") {
			Collections.sort(hotels, new Comparator<HotelsTable>() {
				public int compare(HotelsTable hotel1, HotelsTable hotel2) {
					if (order.equals("desc"))
						return hotel2.getName().compareTo(hotel1.getName());
					else
						return hotel1.getName().compareTo(hotel2.getName());
				}
			});
		}
		return hotels;
	}
	
	private String getDistrictsList() throws IOException {
		String result = "";
		String [] nextLine;
		try {
			File fileDir = new File("districts.csv");
			CSVReader readerDistr = new CSVReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			nextLine = readerDistr.readNext();
			result += "<option value=\"none\">&lt;Без сортировки&gt;</option>";
			while ((nextLine = readerDistr.readNext()) != null) {
				result += "<option value=\"" + nextLine[0] + "\">" + nextLine[0] + "</option>";
			}
			readerDistr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String substitute(String template, String reg, String repl) {
		String result;
		result = template.replaceAll("\\$\\(" + reg + "\\)", repl);
		return result;
	}
}
