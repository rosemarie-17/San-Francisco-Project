package edu.hofstra.sf2;

import java.util.ArrayList;
import java.util.*;

/**
 * This class is used to store all of the Restaurant objects
 * 
 * @author Rosemarie Nasta
 */
public class RestaurantList extends ArrayList<Restaurant>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates and empty RestaurantList object
	 */
	public RestaurantList() {
		super();
	}
	
	/**
	 * Returns a list of Restaurant objects whose names contain keyword as
	 * a substring (case insensitive)
	 * 
	 * @param keyword restaurant name given by the user
	 * @return RestaurantList object with busines names that contain keyword
	 */
	public RestaurantList getMatchingRestaurants(String keyword) {
		if (keyword == null || keyword.equals("")) {
			return null;
		}
		
		RestaurantList filteredList = new RestaurantList();
		// adds the restaurants to filteredList based on the matching names
		for(int i = 0; i < super.size(); i++) {
			if (super.get(i).getName().toLowerCase().contains(keyword) && !(filteredList.contains(super.get(i)))) {
				filteredList.add(super.get(i));
			}
		}
		
		return filteredList;
	}
	
	/**
	 * Returns a list of Restaurant objects whose zip codes are equal
	 * to the keyword
	 * 
	 * @param keyword zip code given by the user
	 * @return RestaurantList object with zip codes that match keyword
	 */
	public RestaurantList getMatchingZips(String keyword) {
		if (keyword == null || keyword.equals("")) {
			return null;
		}
		
		RestaurantList filteredList = new RestaurantList();
		// adds the restaurants to filteredList based on the matching zip codes
		for(int i = 0; i < super.size(); i++) {
			if (super.get(i).getZip().equals(keyword) && !(filteredList.contains(super.get(i)))) {
				filteredList.add(super.get(i));
			}
		}
		
		// sort the list
		Collections.sort(filteredList, Collections.reverseOrder());
		RestaurantList top3 = new RestaurantList();
		int i = 0;
		while (top3.size() < 3) {
			top3.add(filteredList.get(i));
			i++;
		}
		
		return top3;
	}
	
	/**
	 * Returns a restaurant that already exists in the 
	 * RestaurantList object
	 * 
	 * @param the first case of the restaurant in the RestaurantList object
	 * @return restaurant that exists in the object,
	 * 		   null if the restaurant doesn't exist
	 */
	public Restaurant getRestaurants(Restaurant restaurant) {
		Restaurant existingRestaurant = null;
		                                                             
		// verifies if restaurant exists
		for (int i = 0; i < super.size(); i++) {
			if (super.get(i).equals(restaurant)) {
				existingRestaurant = super.get(i);
			}
		}
		
		return existingRestaurant;
	}
	
	/**
	 * Returns a String containing a semi-colon and space
	 * separated list of names of the Restaurant objects
	 * stored in the list
	 * 
	 * @return String with the names of the Restaurants in the list
	 */
	public String toString() {
		String str = "";
		
		for (int i = 0; i < super.size(); i++) {
			str += super.get(i).getName();
			if (i != super.size() - 1) {
				str += "; ";
			}
		}
		
		return str;
	}
}
