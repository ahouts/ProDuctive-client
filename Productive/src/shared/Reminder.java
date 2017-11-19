package shared;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.soap.SOAPBinding.Use;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Reminder {
	private String reminderId = null;
	private String body = null;
	private Date createdDate = null;
	
	public Reminder(JSONObject jsonIn) {
		setReminderId(jsonIn.get(REMINDER_ID_KEY).toString());
		setBody(jsonIn.get(BODY_KEY).toString());
		
		String dateStr = jsonIn.get(CREATED_DATE_KEY).toString();
		
		try {
			createdDate = new DateUtil().parseRFC3339Date(jsonIn.get(CREATED_DATE_KEY).toString());
		} catch (Exception e) {
			System.out.println("Parse error in reminders.java: " + e.getMessage());
		}
	}
	
	public Reminder(String body) {
		setBody(body);
	}
	
	public Reminder() {};
	
	/**
	 * Since editing is not allowed with a reminder the object you can only create a JSONObject not edit it,
	 * Due to this, this method will return a jsonObject with no reminderID
	 * @return 
	 */
	public JSONObject encodeToJson(UserInfo userInfo) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Reminder.BODY_KEY, body);
		jsonObj.put(UserInfo.EMAIL_KEY, userInfo.getUserEmail());
		jsonObj.put(userInfo.PASSWORD_KEY, userInfo.getUserPassword());
		return jsonObj;
	}
	public static JSONObject encodeDeleteJson(UserInfo userInfo) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(UserInfo.EMAIL_KEY, userInfo.getUserEmail());
		jsonObj.put(userInfo.PASSWORD_KEY, userInfo.getUserPassword());
		return jsonObj;
	} 
	
	
	public static ArrayList<Reminder> parseReminder(String input) {
		ArrayList<Reminder> reminders = null;
		System.out.println(input);
		JSONArray jsonArray = null;
		JSONParser parser = new JSONParser();
		try {
			 jsonArray = (JSONArray) parser.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reminders = JsonUtil.makeManyReminders(jsonArray);
				
		return reminders;
	}
	
	@Override
	public String toString() {
		return "Body: " + getBody() + "\n" +
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

	public Date getCreatedDate() {
		return createdDate;
	}


	// Public static variables
	public static final String BODY_KEY = "Body";
	public static final String REMINDER_ID_KEY = "Id";
	public static final String CREATED_DATE_KEY = "CreatedAt";	
}
