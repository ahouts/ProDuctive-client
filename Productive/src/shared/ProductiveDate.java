package shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductiveDate {
	private String day;
	
	public ProductiveDate(String dateIn) {
		day = dateIn;
	}
	
	/**
	 * Creates the current day and time
	 */
	public ProductiveDate() {
		Date today = new Date();
		SimpleDateFormat dateFormatter = productiveDateFormatter();
		day = dateFormatter.format(today);
	}
	
	@Override
	public String toString() {
		return day;
	}
	
	public static SimpleDateFormat productiveDateFormatter() {
		return new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
	}	
	
	
	
}
