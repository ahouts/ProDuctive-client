package shared;
import org.json.simple.JSONObject;

public class Reminder {
	private String reminderId = null;
	private String body = null;
	private ProductiveDate createdDate = null;
	
	public Reminder(JSONObject jsonIn) {
		setReminderId(jsonIn.get(REMINDER_ID_KEY).toString());
		setBody(jsonIn.get(BODY_KEY).toString());
		
		String dateStr = jsonIn.get(CREATED_DATE_KEY).toString();
		
		setCreatedDate(new ProductiveDate(dateStr));
	}
	
	public Reminder(String body) {
		setBody(body);
		createdDate = new ProductiveDate();
	}
	
	public Reminder() {};
	
	/**
	 * Since editing is not allowed with a reminder the object you can only create a JSONObject not edit it,
	 * Due to this, this method will return a jsonObject with no reminderID
	 * @return 
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
