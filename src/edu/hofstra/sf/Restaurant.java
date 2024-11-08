package edu.hofstra.sf2;

import java.util.ArrayList;

/**
 * This class represents restaurants.
 * It stores the name of the business, the address of the business,
 * the business phone number, the zip code, and the list of inspections
 * 
 * @author Rosemarie Nasta
 */
public class Restaurant implements Comparable<Restaurant>{
	private String name;
	private String zip;
	private String address;
	private String number;
	
	private int score;
	private Date date;
	private String violation;
	private String risk;
	private ArrayList<Inspection> inspectionsList = new ArrayList<>();
	
	/**
	 * Initializes a new Restaurant object using the business name and zip code
	 * 
	 * @param name of the business
	 * @param zip code of the business
	 * @throws IllegalArgumentException if the name is an empty string
	 * 		   or the zip code isn't 5 numbers
	 */
	public Restaurant(String name, String zip) throws IllegalArgumentException {
		if (name.equals("") || zip.length() != 5) {
			throw new IllegalArgumentException("Invalid parameter");
		}
		
		this.name = name;
		this.zip = zip;
	}
	
	/**
	 * Initializes a new Restaurant object using the business name, zip code
	 * the address of the restaurant, and its phone number
	 * 
	 * @param name of the business
	 * @param zip code of the business
	 * @param address of the business
	 * @param phone number of the business
	 */
	public Restaurant(String name, String zip, String address, String number) {
		this.name = name;
		this.zip = zip;
		this.address = address;
		this.number = number;
	}
	
	/**
	 * Adds a given inspection to the list of inspections for the
	 * current Restaurant object
	 * 
	 * @param inspection of the current Restaurant to be added to inspectionsList
	 * @throws IllegalArgumentException if the given inspection is null
	 */
	public void addInspection(Inspection inspection) throws IllegalArgumentException {
		if (inspection == null) {
			throw new IllegalArgumentException("Inspection cannot be null");
		}
		
		score = inspection.getScore();
		date = inspection.getDate();
		violation = inspection.getViolation();
		risk = inspection.getRisk();
		
		inspection = new Inspection(score, date, violation, risk);
		inspectionsList.add(inspection);
	}
	
	/**
	 * Formats the given inspection to a String to be added to the
	 * toString() method, assuming the object has a score, date,
	 * risk, and violation
	 * 
	 * @param inspection to be formatted as score, date, risk, violation
	 * @return String in the format score, date, risk, violation
	 */
	public String formatInspection(Inspection inspection) {
		StringBuilder str = new StringBuilder();
		if (inspection.getScore() != 0) {
			str.append(inspection.getScore() + ", ");
		}
		if (inspection.getDate() != null) {
			str.append(inspection.getDate() + ", ");
		}
		if (inspection.getRisk() != null || inspection.getRisk() != "") {
			str.append(inspection.getRisk() + ", ");
		}
		if (inspection.getViolation() != null || inspection.getViolation() != "") {
			str.append(inspection.getViolation() + "\n");
		}
		
		return str.toString();
	}
	
	/**
	 * Formats the given inspection to a String to be added to the
	 * toString() method, assuming the object has a score and date
	 * and no risk or violation
	 * 
	 * @param inspection to be formatted as score, date
	 * @return String in the format score, date
	 */
	public String formatInspectionNoRisk(Inspection inspection) {
		StringBuilder str = new StringBuilder();
		if (inspection.getScore() != 0) {
			str.append(inspection.getScore() + ", ");
		}
		if (inspection.getDate() != null) {
			str.append(inspection.getDate() + "\n");
		}
		
		return str.toString();
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(name + "\n");
		str.append("------------------------------\n");
		str.append("address           : " + address + "\n");
		str.append("zip               : " + zip + "\n");
		str.append("phone             : " + number + "\n");
		str.append("recent inspection risks:\n   ");
		
		// adds the inspections of the Restaurant to str
		
		for (int i = 0; i < inspectionsList.size(); i++) {
			if (inspectionsList.get(i).getRisk() == "" && inspectionsList.get(i).getScore() > 0) {
				str.append("\t" + formatInspectionNoRisk(inspectionsList.get(i)));
			} else if (!(inspectionsList.get(i).getDate() == null) && !(inspectionsList.get(i).getRisk() == "")) {
				str.append("\t" + formatInspection(inspectionsList.get(i)));
			}
		}
		
		return str.toString();
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof Restaurant)) {
			return false;
		}
		
		Restaurant other = (Restaurant) o;
		// if the zips and names of the restaurants are equal
		if (other.name.equalsIgnoreCase(name) && other.zip.equals(zip)) {;
			return true;
		}
		
		return false;
	}
	
	public int compareTo(Restaurant o) {
		// verifies which object is greater based on name
		int minSize = name.length();
		if (o.name.length() < name.length()) {
			minSize = o.name.length();
		}
		// compares each char of the calling object and the parameter object
		for (int i = 0; i < minSize; i++) {
			if (name.charAt(i) < o.name.charAt(i)) {
				return 1;
			} else if (name.charAt(i) > o.name.charAt(i)) {
				return -1;
			}
		}
		
		// if the zip of the calling object is greater the zip of the parameter object
		if (Integer.valueOf(zip) > Integer.valueOf(o.zip)) {
			return 1;
		} else if (Integer.valueOf(zip) < Integer.valueOf(o.zip)) {
			return -1;
		}
		
		// if both the name and the zip are equal
		return 0;
	}
	
	/**
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return zip code of the restaurant
	 */
	public String getZip() {
		return zip;
	}
}
