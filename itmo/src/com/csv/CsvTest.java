package com.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.*;
import com.csv.HotelsTable;

public class CsvTest {
	public static void main(String[] args) throws IOException {
		try {
			String filter = "";
			String order = "asc";
			String template = "";
			template = getTemplate("csv_table.html");
			System.out.println(template);
			template = makeRows(filter, order);
			System.out.println(template);
			// CSVReader reader = new CSVReader(new FileReader("src/com/csv/hotels.csv"));
			// String [] nextLine;
			// while ((nextLine = reader.readNext()) != null) {
			// 	if (nextLine[1].matches("(.*)(?iu)" + filter + "(.*)") == true)
			// 		System.out.println(nextLine[1]);
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static String getTemplate(String templateName) {
		String template = "";
		Scanner sc;
		try {
			sc = new Scanner(new File("war/templates/" + templateName));
			template = sc.useDelimiter("\\Z").next();
			sc.close();
		} catch (FileNotFoundException e) {
			template = e.getMessage();
		}
		return template;
	}

	private static String makeRows(String filter, String order) throws IOException {
		String result = "";
		String template = getTemplate("csv_list_item.html");
		String tmp = "";
		// HashMap<String, String> rowctx = new HashMap();
		try {
			Map<String, String> columnMapping = new HashMap<String, String>();
			columnMapping.put("Name", "name");
			ColumnPositionMappingStrategy<HotelsTable> strat = new ColumnPositionMappingStrategy<>();
			HeaderColumnNameTranslateMappingStrategy<HotelsTable> strategy = new HeaderColumnNameTranslateMappingStrategy<HotelsTable>();
		    strat.setType(HotelsTable.class);
		    String[] columns = new String[] {"name"};
		    //strat.setColumnMapping(columns);
		    //strategy.setColumnMapping(columnMapping);

		    //CsvToBean<HotelsTable> csv = new CsvToBean<>();
		    //List<HotelsTable> list = csv.parse(strategy, reader);

			String [] nextLine;
			
			CSVReader readerDistr = new CSVReader(new FileReader("war/districts.csv"));
			List<DistrictsTable> districts = new ArrayList<DistrictsTable>();
			nextLine = readerDistr.readNext();
			while ((nextLine = readerDistr.readNext()) != null) {
				DistrictsTable district = new DistrictsTable();
				district.setDistrict(nextLine[0]);
				district.setDistrictCode(nextLine[2]);
				districts.add(district);
			}
			readerDistr.close();
			
			CSVReader reader = new CSVReader(new FileReader("war/hotels.csv"));
			List<HotelsTable> hotels = new ArrayList<HotelsTable>();
			nextLine = reader.readNext();
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine[1].matches("(.*)(?iu)" + filter + "(.*)") == true) {
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
			reader.close();
			hotels = sortTable(hotels, order);
			for (HotelsTable hotel: hotels) {
				// List<String> hotelsData = new ArrayList<String>();
				//System.out.print(hotel.getName() + " ");
				//System.out.println(hotel.getDistrictCode());
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
			//for (DistrictsTable distr: districts) {
			//	System.out.println(distr.getDistrict());
			//	System.out.println(distr.getDistrictCode());
			//}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static List<HotelsTable> sortTable(List<HotelsTable> hotels, final String order) {
		Collections.sort(hotels, new Comparator<HotelsTable>() {
			public int compare(HotelsTable hotel1, HotelsTable hotel2) {
				if (order == "desc")
					return hotel2.getName().compareTo(hotel1.getName());
				else
					return hotel1.getName().compareTo(hotel2.getName());
			}
		});
		return hotels;
	}
	
	private static String substitute(String template, String reg, String repl) {
		String result;
		result = template.replaceAll("\\$\\(" + reg + "\\)", repl);
		return result;
	}
}
