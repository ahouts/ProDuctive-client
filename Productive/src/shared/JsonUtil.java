package shared;
import java.util.ArrayList;
import org.json.simple.*;

public class JsonUtil {

	public static ArrayList<Note> makeManyNotes(JSONArray jsonArrayIn) {
		ArrayList<Note> notes = new ArrayList<Note>();
		ArrayList<JSONObject> jsonObjectsToParse = makeManyJSONs(jsonArrayIn);
		
		for(JSONObject jsonObj : jsonObjectsToParse) {
			notes.add(new Note(jsonObj));
		}
		return notes;
	}
	
	public static ArrayList<Reminder> makeManyReminders(JSONArray jsonArrayIn) {
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		ArrayList<JSONObject> jsonObjectsToParse = makeManyJSONs(jsonArrayIn);
		
		for(JSONObject jsonObj : jsonObjectsToParse) {
			reminders.add(new Reminder(jsonObj));
		}
		return reminders;
	}
	
	/*
	 * Helper for the makeManyNotes and makeManyReminders.
	 * @returns an arraylist of the jsonArray in
	 */
	private static ArrayList<JSONObject> makeManyJSONs(JSONArray jsonArrayIn){
		
		ArrayList<JSONObject> jsonObjectsToReturn = new ArrayList<JSONObject>();
		int i = 0;
		while(i<jsonArrayIn.size()) {
			jsonObjectsToReturn.add((JSONObject) jsonArrayIn.get(i));
			i++;
		}
		return jsonObjectsToReturn;
	}
	
}
