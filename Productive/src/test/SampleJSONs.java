package test;
import org.json.simple.*;

import shared.Note;
import shared.ProductiveDate;
import shared.Reminder;

public class SampleJSONs {

	private static JSONObject getNoteJSON(String TITLE_KEY,
				String BODY_KEY,
				ProductiveDate CREATED_DATE_KEY,
				ProductiveDate UPDATED_LAST_DATE_KEY,
				String NOTE_ID_KEY) {
		JSONObject jsonOut = new JSONObject();
		jsonOut.put(Note.TITLE_KEY, TITLE_KEY);
		jsonOut.put(Note.BODY_KEY, BODY_KEY);
		jsonOut.put(Note.CREATED_DATE_KEY, CREATED_DATE_KEY.toString());
		if (UPDATED_LAST_DATE_KEY!=null) {
			jsonOut.put(Note.UPDATED_LAST_DATE_KEY, UPDATED_LAST_DATE_KEY.toString());
		} else {
			jsonOut.put(Note.UPDATED_LAST_DATE_KEY, null);
		}
		jsonOut.put(Note.NOTE_ID_KEY, NOTE_ID_KEY);
		return jsonOut;
	}
	private static JSONObject getReminderJSON(String body,
				ProductiveDate createdDate,
				String reminderId) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Reminder.BODY_KEY, body);
		jsonObj.put(Reminder.CREATED_DATE_KEY, createdDate.toString());
		jsonObj.put(Reminder.REMINDER_ID_KEY, reminderId);
		return jsonObj;
	}
	
	public static JSONObject noteFactory(noteFactoryID id) {
		String TITLE_KEY = null;
		String BODY_KEY = null;
		ProductiveDate CREATED_DATE_KEY = null;
		ProductiveDate UPDATED_LAST_DATE_KEY = null;
		String NOTE_ID_KEY = null;
		if(id==noteFactoryID.ALL_A) {
			TITLE_KEY = "This is a bad title";
			BODY_KEY = "This is a subpar body. I would say it sucks.";
			CREATED_DATE_KEY = new ProductiveDate("01-01-2017:12:30:30");
			UPDATED_LAST_DATE_KEY = new ProductiveDate("01-02-2017:12:45:45");
			NOTE_ID_KEY = "1";
		} else if(id==noteFactoryID.ALL_B) {
			TITLE_KEY = "This is a good title";
			BODY_KEY = "This is a great body. I would say it rocks.";
			CREATED_DATE_KEY = new ProductiveDate("01-03-2017:12:30:30");
			UPDATED_LAST_DATE_KEY = new ProductiveDate("01-04-2017:12:45:45");
			NOTE_ID_KEY = "2";
		} else if (id==noteFactoryID.NULL_BODY) {
			TITLE_KEY = "The body should be null, but this is just a title";
			BODY_KEY =  null;
			CREATED_DATE_KEY = new ProductiveDate("04-01-2017:12:30:30");
			UPDATED_LAST_DATE_KEY = new ProductiveDate("04-04-2017:12:45:45");
			NOTE_ID_KEY = "4";
		} else if(id==noteFactoryID.NULL_UPDATE_DATE) {
			TITLE_KEY = "This is a another title";
			BODY_KEY = "This is a another body. I would say it is ok.";
			CREATED_DATE_KEY = new ProductiveDate("02-01-2017:12:30:30");
			UPDATED_LAST_DATE_KEY = null;
			NOTE_ID_KEY = "3"; }
	
		return getNoteJSON(TITLE_KEY, 
				BODY_KEY, 
				CREATED_DATE_KEY, 
				UPDATED_LAST_DATE_KEY, 
				NOTE_ID_KEY);
	}
	
	public static JSONObject reminderFactory(reminderFactoryID id) {
		 String reminderId = null;
		 String body = null;
		 ProductiveDate createdDate = null;
		 if (id==reminderFactoryID.ALL_A) {
			 reminderId = "1";
			 body = "I really need to remember to feed the chair and steam clean the dog";
			 createdDate = new ProductiveDate("07-04-2013:11:42:41");
		 } else if (id==reminderFactoryID.ALL_B) {
			 reminderId = "2";
			 body = "This body is not nearly as funny";
			 createdDate = new ProductiveDate("01-03-2016:10:02:41");
		 }
		 return getReminderJSON(body, createdDate, reminderId);
	}
	
	public static enum reminderFactoryID {
		ALL_A,ALL_B,ALL_NULL
	}
	public static enum  noteFactoryID{
		ALL_A,ALL_B,NULL_BODY,NULL_UPDATE_DATE,ALL_NULL
	}
	
}

