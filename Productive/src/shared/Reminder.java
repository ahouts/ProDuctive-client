package shared;
import org.json.simple.JSONObject;

import java.util.Map;

public class Reminder {
	private String reminderId;
	private String body;
	private ProductiveDate createdDate;
	
	public Reminder(Map<String, String> jsonIn) {
		setReminderId(jsonIn.get(Reminder.REMINDER_ID_KEY));
		setBody(jsonIn.get(Reminder.BODY_KEY));
		
		String createdDateStr = jsonIn.get(Reminder.CREATED_DATE_KEY);
		
		setCreatedDate(new ProductiveDate(createdDateStr));
	}
	
	public Reminder(String body) {
		setBody(body);
		createdDate = new ProductiveDate();
	}
	
	public Reminder() {};
	
	/**
	 * For use of posting to the server, no reminderID is set yet.
	 * @return JSONObject to post to server.
	 */
	public JSONObject encodeToJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Reminder.BODY_KEY, body);
		jsonObj.put(Reminder.CREATED_DATE_KEY, createdDate.toString());
		return jsonObj;
	}
	
	@Override
	public String toString() {
		return "Body: " + getBody().substring(0, 100) + "\n" +
				"reminderId: " + getReminderId() + "\n" +
				"createdDate: " + getCreatedDate() + "\n";
	}
	// Getters and Setters:
	
	public String getReminderId() {
		return reminderId;
	}

	private void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
	}

	public ProductiveDate getCreatedDate() {
		return createdDate;
	}

	private void setCreatedDate(ProductiveDate createdDate) {
		this.createdDate = createdDate;
	}

	// Public static variables
	public static final String BODY_KEY = "body";
	public static final String REMINDER_ID_KEY = "reminderId";
	public static final String CREATED_DATE_KEY = "created_at";	
}
