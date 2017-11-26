package shared;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Project {
	ArrayList<Note> notes = new ArrayList();
	long projectID = NO_PROJECT_ID;
	String title = null;
	Date createdDate;
	Date updatedDate;
	
	
	public Project(JSONObject jsonObjectIn) {
		this.projectID = (Long) jsonObjectIn.get(PROJECT_ID_KEY);
		this.title = (String) jsonObjectIn.get(PROJECT_TITTLE_KEY);
	}
	
	public Project() {
		this.projectID = NO_PROJECT_ID;
		this.title = UserSuite.allnotesStr;
	}
	public static JSONObject encodeJSON(String titleOfProject, UserInfo myUser) {
		JSONObject jsonToRet = new JSONObject();
		jsonToRet.put(UserInfo.EMAIL_KEY, myUser.getUserEmail());
		jsonToRet.put(UserInfo.PASSWORD_KEY, myUser.getUserPassword());
		jsonToRet.put(PROJECT_TITTLE_KEY, titleOfProject);
		return jsonToRet;
	}
	public static JSONObject encodeShareJSON(Long userIDToShare, UserInfo myUser) {
		JSONObject jsonToRet = new JSONObject();
		jsonToRet.put(UserInfo.EMAIL_KEY, myUser.getUserEmail());
		jsonToRet.put(UserInfo.PASSWORD_KEY, myUser.getUserPassword());
		jsonToRet.put(NEW_USER_ID_KEY, userIDToShare);
		return jsonToRet;
	}
	
	public static ArrayList<Project> makeManyProjects(String jsonStrToParse) {
		ArrayList<Project> projects = new ArrayList<>();
		System.out.println(jsonStrToParse);
		JSONArray jsonArray = null;
		JSONParser parser = new JSONParser();
		try {
			 jsonArray = (JSONArray) parser.parse(jsonStrToParse);
			 ArrayList<JSONObject> jsonObjects =  JsonUtil.makeManyJSONs(jsonArray);
			 for (JSONObject obj: jsonObjects) {
				 projects.add(new Project(obj));
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (projects.isEmpty())
			return null;
		return projects;
	}
	public long getID() {
		return projectID;
	}
public static final long NO_PROJECT_ID = -20312;
public static final String PROJECT_ID_KEY = "Id";
public static final String PROJECT_TITTLE_KEY = "Title";
public static final String NEW_USER_ID_KEY = "NewUserId";
}

