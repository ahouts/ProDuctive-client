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
	
	@Override
	public boolean equals(Object obj) {
		ProductiveDate that = (ProductiveDate) obj;
		if(this.day==null && that.day==null)
			return true;
		if(this.day.equals(that.day))
			return true;
		else
			return false;
	}
	
}