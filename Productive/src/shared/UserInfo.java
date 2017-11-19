package shared;
import org.json.simple.*;

public class UserInfo {
	private String userEmail;
	private String userID = null;
	private String password;

	
	public UserInfo(String userEmail, String password) {
		this.userEmail = userEmail;
		this.password = password;
	}
	public UserInfo(String userEmail, String password, String id) {
		this.userEmail = userEmail;
		this.password = password;
		this.userID = id;
	}

	
	
	
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserPassword() {
		return password;
	}
	public JSONObject encodeToJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(UserInfo.PASSWORD_KEY, this.password );
		jsonObj.put(UserInfo.EMAIL_KEY, this.userEmail);
		return jsonObj;
	}
	
public static String PASSWORD_KEY = "Password";
public static String EMAIL_KEY = "Email";
public static String ID_KEY = "Id";
}
