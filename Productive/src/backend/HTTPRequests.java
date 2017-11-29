package backend;
import org.asynchttpclient.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import shared.*;

import java.io.IOError;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

public class HTTPRequests {
	

	public ArrayList<Note> getNotesFromProject(Long projectID, UserInfo myUserInfo) {
		if (projectID==0) {
			return getAllNotes(myUserInfo);
		}
		
		ArrayList<Note> newNotes = new ArrayList<>();
		try {
			String input = this.getNoteFromProject(myUserInfo, projectID);
			JSONParser parser = new JSONParser();
			newNotes = JsonUtil.makeManyNotes((JSONArray)parser.parse(input));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newNotes;
	}

	public ArrayList<Note> getAllNotes(UserInfo myUserInfo) {
		System.out.println("getting notes");
		ArrayList<Note> newNotes = new ArrayList<>();
		try {
			String input = this.getAllNotesRequest(myUserInfo);
			JSONParser parser = new JSONParser();
			newNotes = JsonUtil.makeManyNotes((JSONArray)parser.parse(input));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newNotes;
	}
	public Note getANote(UserInfo myUserInfo, String noteID) {
		String input = null;
		Note noteToRet = null;
		try {
			input = getANoteRequest(myUserInfo, noteID);
			JSONParser parser = new JSONParser();
			noteToRet = new Note((JSONObject) parser.parse(input));
		} catch (Exception e) {
			System.out.println("get a note failed " + e.getMessage());
		}
		return noteToRet;
	}
	
	
	
	
	
	// Reminders:
	public ArrayList<Reminder> getAllReminders(UserInfo myUserInfo){
		ArrayList<Reminder> reminders = new ArrayList<>();
		String input = null;
		try {
			input = this.reminderRequest(myUserInfo);
		} catch (Exception e) {
			System.out.println("MY EXCEPTION: " + e.getMessage());
			fatalError(e.getMessage());
		}
		if(input!=null) {
			reminders = Reminder.parseReminder(input);
		}
		return reminders;

		}
	public ArrayList<Project> getAllProjects(UserInfo myUser) {
		ArrayList<Project> projectsToReturn = new ArrayList<>();
		String str=null;
		try {
			 str = getProjectRequest(myUser);
			 projectsToReturn = Project.makeManyProjects(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectsToReturn;
	}
	
	public void updateNote(Note updateThisNote, UserInfo myUser) {
		
		try {
			updateNoteRequest(myUser, updateThisNote);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addNote(Note newNote, UserInfo myUser) {
		try {
			addNoteRequest(myUser, newNote);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addProject(String s, UserInfo myUser) {
		
		try {
			addProjectRequest(s,myUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deleteNote(Note noteToDelete, UserInfo myUser) {
		
		try {
			deleteNoteRequest(noteToDelete, myUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deleteProject(Long projectIdToDelete, UserInfo myUser) {
		
		try {
			deleteProjectRequest(projectIdToDelete, myUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void shareProject(Long projectIDToShare, long userIdToShareWith, UserInfo myUser) throws Exception{

		shareProjectRequest(projectIDToShare, userIdToShareWith, myUser);
	
		
	}

	public long getUserID(String userNameToShareWith) throws Exception{
		String password = "The password doesn't really matter here";
	    String output = getUserIDRequest(new UserInfo(userNameToShareWith, password));
	    JSONParser parser = new JSONParser();
	    JSONObject newObj = (JSONObject) parser.parse(output);
		long id =  (long)newObj.get(UserInfo.ID_KEY);
		return id;
	}
	public void shareNote(long noteID, long userIDToShareWith, UserInfo myUser) throws Exception{
		shareNoteRequest(noteID,userIDToShareWith,myUser);
		
	}
	public String getStats() throws Exception {
		String jsonStr = getStatsRequest();
		System.out.println("JSSTR is " + jsonStr);
		JSONParser parser = new JSONParser();
		JSONObject jsObj =  (JSONObject) parser.parse(jsonStr);
		return (String)jsObj.get("Text");
	}
	
	private String getStatsRequest() throws Exception{
		  URL url = new URL(this.getStats);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("GET");
	    
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        return retStr;
	}

	private String getNoteFromProject(UserInfo myUserInfo, Long projectID) throws Exception{
		  URL url = new URL(this.getProjects+"/"+projectID+"/notes");
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	
	private String shareNoteRequest(long noteID, long userIDToShareWith, UserInfo myUser) throws Exception{

		URL url = new URL(getAllNotes+"/"+noteID+"/add_user");
		System.out.println("URL is:" +url.toString());
		
	      int length = Project.encodeShareJSON(userIDToShareWith, myUser).toJSONString().getBytes("UTF-8").length;
	      
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = Project.encodeShareJSON(userIDToShareWith, myUser).toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
		
	}

	private String shareProjectRequest(Long projectIDToShare, long userIdToShareWith, UserInfo myUser) throws Exception{

		URL url = new URL(getProjects+"/"+projectIDToShare+"/add_user");
		System.out.println("URL is:" +url.toString());
		
	      int length = Project.encodeShareJSON(userIdToShareWith, myUser).toJSONString().getBytes("UTF-8").length;
	      
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = Project.encodeShareJSON(userIdToShareWith, myUser).toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
		
	}


	private String deleteProjectRequest(Long projectIdToDelete, UserInfo myUserInfo) throws Exception{
		
		URL url = new URL(deleteProject+projectIdToDelete);
		System.out.println("URL is:" +url.toString());
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
		
	}

	private String deleteNoteRequest(Note deleteThisNote, UserInfo myUserInfo) throws Exception{
		long noteId = Long.parseLong(deleteThisNote.getNoteId());
		URL url = new URL(deleteNote+noteId);
		System.out.println("URL is:" +url.toString());
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
		
	}

	private String addProjectRequest(String s, UserInfo myUserInfo) throws Exception {
		URL url = new URL(getProjects);
	      int length = Project.encodeJSON(s, myUserInfo).toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = Project.encodeJSON(s, myUserInfo).toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
		
	}

	private String addNoteRequest(UserInfo myUserInfo, Note updateThisNote) throws Exception {
		String NoteID = updateThisNote.getNoteId();
		URL url = new URL(getAllNotes);
		System.out.println("URL is:" +url.toString());
	      int length = updateThisNote.newNoteEncodeToJSON(myUserInfo).toJSONString().getBytes("UTF-8").length;
	      
	      System.out.println("the new note in json is:");
	      System.out.println(updateThisNote.newNoteEncodeToJSON(myUserInfo).toJSONString());
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = updateThisNote.newNoteEncodeToJSON(myUserInfo).toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}

	private String updateNoteRequest(UserInfo myUserInfo, Note updateThisNote) throws Exception {
		String NoteID = updateThisNote.getNoteId();
		URL url = new URL(getAllNotes+"/"+NoteID);
		System.out.println("URL is:" +url.toString());
	      int length = updateThisNote.encodeToJson(myUserInfo).toJSONString().getBytes("UTF-8").length;
	      
	      System.out.println("the new note in json is:");
	      System.out.println(updateThisNote.encodeToJson(myUserInfo).toJSONString());
	      
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = updateThisNote.encodeToJson(myUserInfo).toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	private String getANoteRequest(UserInfo myUserInfo, String NoteID) throws Exception {
		URL url = new URL(getAllNotes+"/"+NoteID);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	private String getAllNotesRequest(UserInfo myUserInfo) throws Exception {
		URL url = new URL(getAllNotes);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	private String getProjectRequest(UserInfo myUserInfo) throws Exception {
		URL url = new URL(getProjects);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Failed Project : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	public String reminderRequest(UserInfo myUserInfo) throws Exception {
	      URL url = new URL(getReminderURL);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Faileds : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        while ((output = br.readLine()) != null) {
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	public void insertNewReminder(UserInfo myUserInfo, String newReminderStr) {
		try {
		Reminder reminder = new Reminder(newReminderStr);
		JSONObject jsonToUse = reminder.encodeToJson(myUserInfo);
		 URL url = new URL(insertReminderURL);
		 
	      int length = jsonToUse.toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = jsonToUse.toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Faileds : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            	retStr+=output;
	        }
	        conn.disconnect();
		} catch (Exception e) {
			System.out.println("Exception inserting error");
		}
	        
		
		
	}
	
	public void deleteReminder(UserInfo myUser, String idToDelete) {
		try {
			JSONObject jsonToUse = Reminder.encodeDeleteJson(myUser);
			 URL url = new URL(deleteReminderURL+idToDelete);
			 
		      int length = jsonToUse.toJSONString().getBytes("UTF-8").length;
		      String lengthStr = Integer.toString(length);
		      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		      conn.setDoOutput(true);
		      conn.setRequestMethod("PUT");
		      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		      conn.setRequestProperty("Content-Length", lengthStr);
		      
		      String strToWrite = jsonToUse.toJSONString() + "\n";
		      
		      OutputStream os = conn.getOutputStream();
		      os.write(strToWrite.getBytes("UTF-8"));
		      os.flush();
		      os.close();
		      
		      
		      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
		    	  		byte[] bytes = new byte[5000];
		    	  		conn.getErrorStream().read(bytes);
		    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
		    	  		throw new RuntimeException("Faileds : HTTP error code : "
		                + conn.getResponseCode()+ conn.getResponseMessage());
		            
		      }	   
		      
		      BufferedReader br = new BufferedReader(new InputStreamReader(
		                (conn.getInputStream())));

		        String output;
		        String retStr="";
		        System.out.println("Output from Server .... \n");
		        while ((output = br.readLine()) != null) {
		            System.out.println(output);
		            	retStr+=output;
		        }
		        conn.disconnect();
			} catch (Exception e) {
				System.out.println("Exception inserting error");
			}
		        
			
		
	}
	public void createAccount(UserInfo myUserInfo) throws Exception {
		
	      URL url = new URL(createUserURL);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Faileds : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	}
	
	private String getUserIDRequest(UserInfo myUserInfo) throws Exception{
		  URL url = new URL(getUserIdURL);
	      int length = myUserInfo.encodeToJson().toJSONString().getBytes("UTF-8").length;
	      String lengthStr = Integer.toString(length);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("PUT");
	      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	      conn.setRequestProperty("Content-Length", lengthStr);
	      
	      String strToWrite = myUserInfo.encodeToJson().toJSONString() + "\n";
	      
	      OutputStream os = conn.getOutputStream();
	      os.write(strToWrite.getBytes("UTF-8"));
	      os.flush();
	      os.close();
	      
	      
	      if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
	    	  		byte[] bytes = new byte[5000];
	    	  		conn.getErrorStream().read(bytes);
	    	  		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	    	  		throw new RuntimeException("Faileds : HTTP error code : "
	                + conn.getResponseCode()+ conn.getResponseMessage());
	            
	      }	   
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        String retStr="";
	       // System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            //System.out.println(output);
	            	retStr+=output;
	        }
	        conn.disconnect();
	        
	        
	        return retStr;
	}
	
	public UserInfo checkUserInfo(UserInfo userInfo) {
		String toParse = null;
		try {
			reminderRequest(userInfo);
		} catch (Exception e ) {
			System.out.println(e.getMessage());
			return null;
		}
		try {
			toParse = getUserIDRequest(userInfo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JSONParser parser = new JSONParser();
		String id = null;
		try {
		JSONObject newObj = (JSONObject) parser.parse(toParse);
		id =  Long.toString((long) newObj.get(UserInfo.ID_KEY));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new UserInfo(userInfo.getUserEmail(), userInfo.getUserPassword(), id);
	}
	
	public static void fatalError(String errorStr) 
	{
			JOptionPane.showMessageDialog(null, "An error has occured.");
			return;
	}

	private static final String host = "https://productive.ahouts.com/";
	private static final String getReminderURL = host + "reminder";
	private static final String insertReminderURL = host + "reminder";
	private static final String deleteReminderURL = host + "reminder/delete/";
	private static final String createUserURL = host + "user";
	private static final String getUserIdURL = createUserURL + "/getid";
	private static final String getProjects =  host + "project";
	private static final String getAllNotes = host + "note";
	private static final String deleteNote = getAllNotes + "/delete/";
	private static final String deleteProject = getProjects + "/delete/";
	private static final String getStats = host + "stats";
	

	
	
	
	
	
	
	
	
	

	
}
