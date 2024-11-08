package edu.hofstra.sf2;

/**
 * This class is used to represent the particular
 * inspection of a restaurant.
 * It stores the date, score, risk category, and violation
 * 
 * @author Rosemarie Nasta
 */
public class Inspection {
	private Date date;
	private int score;
	private String violation;
	private String risk;
	
	/**
	 * Initializes a new Inspection object
	 * 
	 * @param date given by the Date class
	 * @param score int score under 100
	 * @param violation description of the restaurant's violation
	 * @param risk category of the inspection
	 * @throws IllegalArgumentException if the score is greater than 100
	 */
	public Inspection ( int score, Date date,String violation, String risk) throws IllegalArgumentException{
		if (score > 100) {
			throw new IllegalArgumentException("Invalid score");
		}
		
		this.date = date;
		this.score = score;
		this.violation = violation;
		this.risk = risk;
	}
	
	/**
	 * @return date of this Inspection object
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return score of this Inspection object
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @return violation description of this Inspection Object
	 */
	public String getViolation() {
		return violation;
	}
	
	/**
	 * @return risk category of this Inspection object
	 */
	public String getRisk() {
		return risk;
	}
}
