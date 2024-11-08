package edu.hofstra.sf2;

/**
 * This class represents the date that an
 * inspection was completed on
 * 
 * @author Rosemarie Nasta
 */
public class Date implements Comparable<Date>{
	private int month;
	private int day;
	private int year;
	
	/**
	 * Initializes a new Date object using a String
	 * 
	 * @param date String in format MM/DD/YYYY or MM/DD/YY
	 * @throws IllegalArgumentException when date is null
	 */
	public Date (String date) throws IllegalArgumentException {
		if (date == null)
			throw new IllegalArgumentException("This date is null");
		
		// if the length of month isn't 2 or the length of day isn't 2
		if (date.split("/")[0].length() != 2 || date.split("/")[1].length() != 2 )
			throw new IllegalArgumentException("Date invalid");
				
		this.month = Integer.valueOf(date.split("/")[0]);
		this.day = Integer.valueOf(date.split("/")[1]);
		this.year = Integer.valueOf(date.split("/")[2]);
	}
	
	/**
	 * Initializes a new Date object using three ints
	 * 
	 * @param month int between 1 and 12
	 * @param day int between 1 and 31
	 * @param year int between 2000 and 2025
	 * @throws IllegalArgumentException for any int out of range
	 */
	public Date (int month, int day, int year) throws IllegalArgumentException {
		if (month > 12 || month < 1) 
			throw new IllegalArgumentException("Invalid month");
		
		if (year < 2000 || year > 2025)
			throw new IllegalArgumentException("Invalid year");
		
		if (day > 31 || day < 1)
			throw new IllegalArgumentException("Invalid day");
		
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	/**
	 * Uses month, day, and year variables and properly formats
	 * them to be in MM/DD/YYYY form
	 * 
	 * @return a String in the form MM/DD/YYYY
	 */
	public String toString() {
		String date = "";
		
		// if the month is 1 digit, add 0 in front of month for the MM format
		if (this.month < 10)
			date += "0" + this.month + "/";
		else
			date += this.month + "/";
		
		// if the day is 1 digit, add 0 in front of day for the DD format
		if (this.day < 10)
			date += "0" + this.day + "/";
		else
			date += this.day + "/";
		
		// if the year is 2 digits, add 20 to the front of year for YYYY format
		if (this.year < 100 && this.year > 9)
			date += "20" + this.year;
		// if the year is 1 digit, add 200 to the front of year for YYYY format
		else if (this.year < 10)
			date += "200" + this.year;
		else
			date += this.year;
		
		return date;
	}

	/**
	 * Compares two Date objects.
	 * If the year, month, or day of the calling Date is greater, 1 is returned
	 * If the year, month, or day of the parameter Date is greater, -1 is returned
	 * If the two dates are equal, 0 is returned
	 * 
	 * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Date o) {
		if (o == null)
			return 1;
		
		// if-statements for when the calling object is greater than the parameter object
		if (this.year > o.year)
			return 1;
		else if (this.year == o.year && this.month > o.month)
			return 1;
		else if (this.year == o.year && this.month == o.month && this.day > o.day)
			return 1;
		
		// if-statements for when the parameter object is greater than the calling object
		if (this.year < o.year)
			return -1;
		else if (this.year == o.year && this.month < o.month)
			return -1;
		else if (this.year == o.year && this.month == o.month && this.day < o.day)
			return -1;
		
		// returns if the two Dates are equal
		return 0;
	}
}
