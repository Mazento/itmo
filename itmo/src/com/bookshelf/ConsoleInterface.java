package com.bookshelf;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


public class ConsoleInterface {

	public static String command = "";
    public static String firstArg = "";
	public static String[] genreList = {"mystery", "fantasy", "etc"};
	
    public static void main(String[] args) {
        if (args.length != 0) {
        	command = args[0];
            if (args.length > 1)
                firstArg = args[1];
        }

        
        switch (command) {
            case "-d": 
                getDescription();
                break;
            case "-genre":
                if (firstArg != "") {
                    getBooksByGenre(firstArg);
                    break;
                }
            default:
                listCommands();
        }
    }

    private static void getDescription() {
        System.out.println("This is bookshelf. Here you will find a humble collection of some masterpiece books");
    }

    private static void getBooksByGenre(String genre) {
        if (Arrays.asList(genreList).contains(genre)) {
        	System.out.println(genre + " books:");
        	switch (genre) {
        		case "mystery":
                    System.out.println("Jim Butcher - Dresden Files");
                    System.out.println("Derek Landy - Skulduggery Pleasant");
        			System.out.println("Stephen King - Dark Tower");
                    break;
                case "fantasy":
                    System.out.println("Brent Weeks - Lightbringer");
                    System.out.println("Brent Weeks - Night Angel");
                    System.out.println("Patrick Rothfuss - The Kingkiller Chronicle");
                    System.out.println("Joe Abercrombie - The First Law");
                    break;
                case "etc":
                    System.out.println("Kevin Mitnick - Art of Deception");
                    System.out.println("Daniel Kiz - Billy Milligan");
                    System.out.println("Robert Sawer - End of an Era");
                    break;
        	}
        }
        else {
        	System.out.println("No such genre. Please, select on of the following:");
            for (Iterator<String> i = Arrays.asList(genreList).iterator(); i.hasNext();) {
                String item = i.next();
                System.out.println(" - " + item);
            }
        }
    }

    private static void listCommands() {
        System.out.println("List of commands:");
        System.out.println("-d: get description");
        System.out.println("-genre [genre]: get list of books with specified genre");
    }
        
}
