package shared;
import java.util.Date;

import org.json.simple.*;
import org.json.simple.JSONObject;

public class Note {
	private Date createdDate = null;
	private Date updatedLastDate = null;
	private String body = null;
	private String title = null;
	private String noteId = null;
	
	public Note(JSONObject jsonIn) {
		setTitle(jsonIn.get(TITLE_KEY).toString());
		
		if(jsonIn.get(BODY_KEY)!=null) {
			setBody(jsonIn.get(BODY_KEY).toString());
		} else {setBody(null);}
		
		setNoteId(jsonIn.get(NOTE_ID_KEY).toString());
		
		String updatedDateStr = null;
		String createdDateStr = jsonIn.get(CREATED_DATE_KEY).toString();
		if(jsonIn.get(UPDATED_LAST_DATE_KEY)!=null) {
			updatedDateStr = jsonIn.get(UPDATED_LAST_DATE_KEY).toString();
		}
		try {
		createdDate = new DateUtil().parseRFC3339Date(jsonIn.get(CREATED_DATE_KEY).toString());
		updatedLastDate = new DateUtil().parseRFC3339Date(jsonIn.get(UPDATED_LAST_DATE_KEY).toString());
		} catch (Exception e) {
			System.out.println("Parese error for date in note.java: " + e.getMessage());
		}
	}
	
	public Note() {
	}
	
	/**
	 * 
	 * @return JSON of the note object according to agreed upon schema
	 */
	public JSONObject encodeToJson() {
		JSONObject jsonOut = new JSONObject();
		jsonOut.put(TITLE_KEY, getTitle());
		jsonOut.put(BODY_KEY, getBody());
		jsonOut.put(CREATED_DATE_KEY, getCreatedDate().toString());
		jsonOut.put(UPDATED_LAST_DATE_KEY, getUpdatedLastDate().toString());
		jsonOut.put(NOTE_ID_KEY, getNoteId());
		return jsonOut;
	}
	
	@Override
	public String toString() {
		return "Title: " + title + "\n" +
				"NoteId: " + noteId + "\n" +
				"Created: " + createdDate + "\n" +
				"Updated: " + updatedLastDate + "\n" +
				"Body: " + body.substring(0, 100) + "\n" ;
	}
	
	
// Getters and Setters:
	public Date getCreatedDate() {
		return createdDate;
	}

	

	public Date getUpdatedLastDate() {
		return updatedLastDate;
	}



	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
	public static final String CREATED_DATE_KEY = "created_at";
	public static final String UPDATED_LAST_DATE_KEY = "updated_at";
	public static final String BODY_KEY = "body";
	public static final String TITLE_KEY = "title";
	public static final String NOTE_ID_KEY = "note_id";
}
