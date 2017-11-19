package backend;
import org.asynchttpclient.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import shared.*;

import java.io.IOError;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class HTTPRequests {
	

	// Reminders:
	public ArrayList<Reminder> getAllReminders(UserInfo myUserInfo){
		ArrayList<Reminder> reminders = new ArrayList<>();
		String input = null;
		try {
			input = this.reminderRequest(myUserInfo);
		} catch (Exception e) {
			System.out.println("MY EXCEPTION: " + e.getMessage());
		}
		if(input!=null) {
			reminders = Reminder.parseReminder(input);
		}
		return reminders;

		}
	
	
	
	
	
	private String reminderRequest(UserInfo myUserInfo) throws Exception {
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
	        String returnThisCrap="";
	        while ((output = br.readLine()) != null) {
	        		System.out.println(output);
	            	returnThisCrap+=output;
	        }
	        conn.disconnect();
	        
	        
	        return returnThisCrap;
	}
	public void insertNewReminder(UserInfo myUserInfo, String newReminderStr) {
		try {
		Reminder reminder = new Reminder(newReminderStr);
		JSONObject jsonToUse = reminder.encodeToJson(myUserInfo);
		System.out.println("INSERT JSON: ");
		System.out.println(jsonToUse.toString());
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
	        String returnThisCrap="";
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	            	returnThisCrap+=output;
	        }
	        conn.disconnect();
		} catch (Exception e) {
			System.out.println("Exception inserting error");
		}
	        
		
		
	}
	public void deleteReminder(UserInfo myUser, String idToDelete) {
		try {
			JSONObject jsonToUse = Reminder.encodeDeleteJson(myUser);
			
			System.out.println("INSERT JSON: ");
			System.out.println(jsonToUse.toString());
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
		        String returnThisCrap="";
		        System.out.println("Output from Server .... \n");
		        while ((output = br.readLine()) != null) {
		            System.out.println(output);
		            	returnThisCrap+=output;
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
	        String returnThisCrap="";
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	            	returnThisCrap+=output;
	        }
	        conn.disconnect();
	        
	        System.out.println(returnThisCrap);
	}
	
	private String getUserID(UserInfo myUserInfo) throws Exception{
		  URL url = new URL(getUserIdURL);
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
	        String returnThisCrap="";
	       // System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            //System.out.println(output);
	            	returnThisCrap+=output;
	        }
	        conn.disconnect();
	        
	        
	        return returnThisCrap;
	}
	
	public UserInfo checkUserInfo(UserInfo userInfo) {
		UserInfo userInfoToReturn = null;
		String toParse = null;
		try {
			reminderRequest(userInfo);
		} catch (Exception e ) {
			System.out.println(e.getMessage());
			return null;
		}
		try {
			toParse = getUserID(userInfo);
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

	//https://productive.ahouts.com/reminder/get
	private static final String host = "https://productive.ahouts.com/";
	private static final String getReminderURL = host + "reminder";
	private static final String insertReminderURL = host + "reminder";
	private static final String deleteReminderURL = host + "reminder/delete/";
	private static final String createUserURL = host + "user";
	private static final String getUserIdURL = host + "get_user_id";

	
}
