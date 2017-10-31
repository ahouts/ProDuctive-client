package shared;
import org.json.simple.*;
import org.json.simple.JSONObject;

public class Note {
	private ProductiveDate createdDate = null;
	private ProductiveDate updatedLastDate = null;
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

		setCreatedDate(new ProductiveDate(createdDateStr));
		setUpdatedLastDate(new ProductiveDate(updatedDateStr));
	}
	
	public Note() {
		createdDate = new ProductiveDate();
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
	public ProductiveDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ProductiveDate createdDate) {
		this.createdDate = createdDate;
	}

	public ProductiveDate getUpdatedLastDate() {
		return updatedLastDate;
	}

	public void setUpdatedLastDate(ProductiveDate updatedLastDate) {
		this.updatedLastDate = updatedLastDate;
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
