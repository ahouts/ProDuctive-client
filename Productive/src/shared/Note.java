package shared;
import java.util.Date;

import javax.jws.soap.SOAPBinding.Use;

import org.json.simple.*;
import org.json.simple.JSONObject;

public class Note {
	private Date createdDate = null;
	private Date updatedLastDate = null;
	private String body = null;
	private String title = null;
	private String noteId = null;
	
	// added stuff
	private String projectId = null;
	private String ownnerId = null;
	private long projectID = 0;
	private boolean projectValid = false;
	
	public Note(JSONObject jsonIn) {
		setTitle(jsonIn.get(TITLE_KEY).toString());
		
		if(jsonIn.get(BODY_KEY)!=null) {
			setBody(jsonIn.get(BODY_KEY).toString());
		} else {setBody(null);}
		
		setNoteId(jsonIn.get(NOTE_ID_KEY).toString());
		
		String createdDateStr = "";
		String updatedDateStr = "";
		if(jsonIn.get(UPDATED_LAST_DATE_KEY)!=null) {
			updatedDateStr = jsonIn.get(UPDATED_LAST_DATE_KEY).toString();
		}
		if(jsonIn.get(CREATED_DATE_KEY)!=null) {
			createdDateStr = jsonIn.get(CREATED_DATE_KEY).toString();
		}
		try {
		createdDate = new DateUtil().parseRFC3339Date(jsonIn.get(CREATED_DATE_KEY).toString());
		updatedLastDate = new DateUtil().parseRFC3339Date(jsonIn.get(UPDATED_LAST_DATE_KEY).toString());
		} catch (Exception e) {
			System.out.println("Parese error for date in note.java: " + e.getMessage());
		}
		// added stuff
		this.ownnerId = jsonIn.get(OWNER_ID_KEY).toString();
		
		JSONObject projectJSON = (JSONObject)jsonIn.get(PROJECT_ID_KEY);
		this.projectValid = (boolean)projectJSON.get(PROJECT_ID_VALID_KEY);
		this.projectID = (long)projectJSON.get(PROJECT_ID_NUM_KEY);
	}
	
	public Note() {
		this.noteId = NEW_NOTE_ID;
	}
	
	/**
	 * 
	 * @return JSON of the note object according to agreed upon schema
	 */
	
	public JSONObject encodeToJson(UserInfo userInfo) {
		JSONObject jsonOut = new JSONObject();
		JSONObject nestedJSON = new JSONObject();
		nestedJSON.put(PROJECT_ID_NUM_KEY, this.projectID);
		nestedJSON.put(PROJECT_ID_VALID_KEY, this.projectValid);
		
		
		jsonOut.put(UserInfo.EMAIL_KEY, userInfo.getUserEmail());
		jsonOut.put(UserInfo.PASSWORD_KEY, userInfo.getUserPassword());
		jsonOut.put(TITLE_KEY, getTitle());
		jsonOut.put(BODY_KEY, getBody());
		// Do I need this for creating notes?
		if (ownnerId!=null)
			jsonOut.put(OWNER_ID_KEY, Long.parseLong(ownnerId));
		jsonOut.put(PROJECT_ID_KEY, nestedJSON);
		
		return jsonOut;
	}
	
	//dat = { 'Email': sys.argv[1], 'Password': sys.argv[2], 'Title': sys.argv[3], 'Body': sys.argv[4], 'ProjectId': projectid}
	public JSONObject newNoteEncodeToJSON(UserInfo userInfo) {
		JSONObject jsonOut = new JSONObject();
		JSONObject nestedJSON = new JSONObject();
		nestedJSON.put(PROJECT_ID_NUM_KEY, this.projectID);
		nestedJSON.put(PROJECT_ID_VALID_KEY, this.projectValid);
		jsonOut.put(UserInfo.EMAIL_KEY, userInfo.getUserEmail());
		jsonOut.put(UserInfo.PASSWORD_KEY, userInfo.getUserPassword());
		jsonOut.put(TITLE_KEY, getTitle());
		jsonOut.put(BODY_KEY, getBody());
		jsonOut.put(PROJECT_ID_KEY, nestedJSON);
		
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
	public long getProjectId() {
		return this.projectID;
	}
	public boolean isNew() {
		if (this.noteId.equals(NEW_NOTE_ID))
			return true;
		else
			return false;
	}
	public void setProjectID(long selectedProjectProperID) {
		this.projectID = selectedProjectProperID;
		if (selectedProjectProperID==0) {
			this.projectValid = false;
		} else {
			this.projectValid = true;
		}
		
	}
	public void setOwnerID(long l) {
		this.ownnerId = ""+l;
	}
	
	
	public static final String NEW_NOTE_ID = "This is a new note please do not try to update another note";
	public static final String BODY_KEY = "Body";
	public static final String TITLE_KEY = "Title";
	public static final String NOTE_ID_KEY = "Id";
	public static final String OWNER_ID_KEY = "OwnerId";
	public static final String PROJECT_ID_KEY = "ProjectId";
	public static final String PROJECT_ID_NUM_KEY = "Int64";
	public static final String PROJECT_ID_VALID_KEY = "Valid";
	public static final String CREATED_DATE_KEY = "CreatedAt";
	public static final String UPDATED_LAST_DATE_KEY = "UpdatedAt";

	
}

