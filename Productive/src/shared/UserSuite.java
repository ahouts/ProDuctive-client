package shared;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import backend.HTTPRequests;

public class UserSuite {

	private ArrayList<Reminder> remindersArray = new ArrayList();
	private ArrayList<Note> notes = new ArrayList<>();
	//private ArrayList<Praoject>
	public  DefaultListModel<String> ReminderList = new <String>DefaultListModel();
	public  DefaultListModel<String> ProjectList = new <String>DefaultListModel();
	public  DefaultListModel<String> NoteList = new <String>DefaultListModel();
	public  Note ActiveNote = new Note();
	public  UserInfo myUser;
	
	public UserSuite(UserInfo userToUse) {
		myUser = userToUse;
		refreshProjectList();
		refreshReminders();
	}
	
	public  DefaultListModel<String> refreshProjectList() {
		
		return ProjectList;
	}
	public  DefaultListModel<String> refreshNoteList(String titleOfProject) {
		if (titleOfProject == null)
			return NoteList;
		return NoteList;
	}
	public  DefaultListModel<String> refreshReminders() {
		ReminderList.clear();
		remindersArray.clear();
		ArrayList<Reminder> newReminders = new HTTPRequests().getAllReminders(myUser);
		for(Reminder r: newReminders) {
			ReminderList.addElement(r.getBody() + " | Created:" + r.getCreatedDate());
			remindersArray.add(r);
		}
		
		return ReminderList;
	}
	public  void refreshAll() {
		refreshNoteList(null);
	}
	public void insertNewReminder(String reminderText) {
		new HTTPRequests().insertNewReminder(myUser, reminderText);
	}
	public void deleteReminder(int indexToDelete) {
		String idToDelete = remindersArray.get(indexToDelete).getReminderId();
		new HTTPRequests().deleteReminder(myUser, idToDelete);
	}
	
	public  String getUserName() {
		return myUser.getUserEmail();
	}

}
