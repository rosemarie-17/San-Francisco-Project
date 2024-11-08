package edu.hofstra.sf2;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class SFRestaurantData {

	public static void main(String[] args) throws IOException {
		// verify that command line argument contains a name of an existing file
		File file = new File("SF_restaurant_scores_full.csv");
		if (!file.exists()) {
			System.err.println("Error: the file " + file.getAbsolutePath() + " does not exist.\n");
			System.exit(1);
		}
		
		if (!file.canRead()) {
			System.err.println("Error: the file " + file.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}
		
		// open the file for reading
		Scanner in = null;
		
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file " + file.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}
		
		// read the content of the file and save the data in a restaurant list
		String line = null;
		Scanner parseLine = null;
		RestaurantList restaurantList = new RestaurantList();
		in.nextLine();
		
		while (in.hasNextLine()) {
			try {
				line = in.nextLine();
				parseLine = new Scanner(line);
				
				// creates a Restaurant object using the current name, zip, address, and phone number
				String business = splitCSVLine(line).get(1);
				String zipCode = splitCSVLine(line).get(5);
				String address = splitCSVLine(line).get(2);
				String phone = splitCSVLine(line).get(9);
				Restaurant currentRestaurant = new Restaurant(business, zipCode, address, phone);
				
				// creates a Date object using the current inspection date and getting the values between slashes
				String date = splitCSVLine(line).get(11).split(" ")[0];
				int month = Integer.valueOf(date.split("/")[0]);
				int day = Integer.valueOf(date.split("/")[1]);
				int year = Integer.valueOf(date.split("/")[2]);
				Date currentDate = new Date(month, day, year);
				
				// creates an Inspection object using the current score, Date, violation, and risk
				int score;
				// if there is no score
				if (splitCSVLine(line).get(12).equals(""))
					score = 0;
				else 
					score = Integer.valueOf(splitCSVLine(line).get(12));
				String violation = splitCSVLine(line).get(15);
				String risk;
				if (splitCSVLine(line).size() <= 16) 
					risk = "";
				else
					risk = splitCSVLine(line).get(16);
				Inspection currentInspection = new Inspection(score, currentDate, violation, risk);
				
				// verify that the current Restaurant object is already in restaurantList
				Restaurant existingRestaurant = restaurantList.getRestaurants(currentRestaurant);
				if (existingRestaurant != null) {
					existingRestaurant.addInspection(currentInspection);
				} else {
					currentRestaurant.addInspection(currentInspection);
					restaurantList.add(currentRestaurant);
				}
			}
			catch (NoSuchElementException ex){
				// caused by an incomplete or miss-formatted line in the input file
				System.err.println(line);
				continue;
			}
			
		}
		
		String userValue = null;
		Scanner userInput = new Scanner(System.in);
		
		printMenu();
		// get the value from the user
		userValue = userInput.nextLine();
		
		while (!userValue.equalsIgnoreCase("quit")) {
			// splits the user input into the first query and the keyword
			String[] userValues = userValue.split(" ");
			String firstValue = userValues[0];
			String secondValue = userValues[1];
			RestaurantList filteredRestaurants = new RestaurantList();
				
			if (firstValue.equalsIgnoreCase("name")) {
				filteredRestaurants = restaurantList.getMatchingRestaurants(secondValue);
				// sorts filteredRestaurants in alphabetical order
				Collections.sort(filteredRestaurants);
				
				for (int i = 0; i < filteredRestaurants.size(); i++) {
					System.out.println(filteredRestaurants.get(i));
				}
				
			} else if (firstValue.equalsIgnoreCase("zip")) {
				filteredRestaurants = restaurantList.getMatchingZips(secondValue);
				// sorts filteredRestaurants in ascending order based on zip code
				Collections.sort(filteredRestaurants);
				Collections.reverse(filteredRestaurants);
				
				for (int i = 0; i < filteredRestaurants.size(); i++) {
					System.out.println(filteredRestaurants.get(i));
				}
				
			} else {
				System.out.println("This is not a valid query. Try again.");
			}
			
			// get the value from the user
			System.out.println("Enter your search query");
			userValue = userInput.nextLine();
		}
		
		userInput.close();
		parseLine.close();
		
	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * 
	 * @author CSV
	 * 
	 * @param textLine a line of text to be passed
	 * @return an ArrayList of objects containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){
		 if (textLine == null ) return null;
		 
		 ArrayList<String> entries = new ArrayList<String>();
		 int lineLength = textLine.length();
		 StringBuffer nextWord = new StringBuffer();
		 char nextChar;
		 boolean insideQuotes = false;
		 boolean insideEntry = false;
		 
		 // iterate over all characters in the textLine
		 for (int i = 0; i < lineLength; i++) {
			 nextChar = textLine.charAt(i);
			 
			 // handle smart quotes as well as regular quotes
			 if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {
				 
				 // change insideQuotes flag when nextChar is a quote
				 if (insideQuotes) {
					 insideQuotes = false;
					 insideEntry = false;
				 } else {
					 insideQuotes = true;
					 insideEntry = true;
				 }
			 } else if (Character.isWhitespace(nextChar)) {
				 if (insideQuotes || insideEntry) {
					 // add it to the current entry
					 nextWord.append(nextChar);
				 } else { // skip all spaces between entries
					 continue;
				 }
			 } else if (nextChar == ',') {
				 if (insideQuotes) { // comma inside an entry
					 nextWord.append(nextChar);
				 } else { // end of entry found
					 insideEntry = false;
					 entries.add(nextWord.toString());
					 nextWord = new StringBuffer();
				 }
			 } else {
				 // add all other characters to the nextWord
				 nextWord.append(nextChar);
				 insideEntry = true;
			 }
		 }
		 
		 // add the last word (assuming not empty)
		 // trim the white space before adding to the list
		 if (!nextWord.toString().equals("")) {
			 entries.add(nextWord.toString().trim());
		 }
		 
		 return entries;
	}
	
	/**
	 * A method to print the menu shown to the user
	 */
	public static void printMenu() {
		System.out.println("Search the database by matching keywords to postal codes or restaurant names");
		System.out.println("To search for matching restaurant names, enter");
		System.out.println("\tname KEYWORD");
		System.out.println("To search for restaurants in a zip code, enter");
		System.out.println("\tzip KEYWORD");
		System.out.println("To finish the program, enter");
		System.out.println("\tquit");
		System.out.println("Enter your search query");
	}
}
