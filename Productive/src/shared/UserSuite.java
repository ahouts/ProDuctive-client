package shared;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

import backend.HTTPRequests;

public class UserSuite {

	private ArrayList<Reminder> remindersArray = new ArrayList();
	private ArrayList<Note> notes = new ArrayList<>();
	public ArrayList<Project> projects = new ArrayList<>();
	public  DefaultListModel<String> ReminderList = new <String>DefaultListModel();
	public  DefaultListModel<String> ProjectList = new <String>DefaultListModel();
	public  DefaultListModel<String> NoteList = new <String>DefaultListModel();
	public  Note ActiveNote = new Note();
	public  UserInfo myUser;
	
	public UserSuite(UserInfo userToUse) {
		myUser = userToUse;
		refreshAll();
	}
	
	public  DefaultListModel<String> refreshProjectList() {
		projects.clear();
		ProjectList.clear();
		projects.add( new Project());
		ProjectList.addElement(allnotesStr);
		
		ArrayList<Project> newProjects = new HTTPRequests().getAllProjects(myUser);
		if(newProjects!=null && !newProjects.isEmpty())
		for(Project proj:newProjects) {
			projects.add(proj);
			ProjectList.addElement(proj.title);
		}
		
		return ProjectList;
	}
	
	public  DefaultListModel<String> refreshNoteList(Long IDofProject) {
		notes.clear();
		NoteList.clear();
		ArrayList<Note> newNotes = new ArrayList<>();
		if (IDofProject==Project.NO_PROJECT_ID)
			newNotes = new HTTPRequests().getAllNotes(this.myUser);
		else
			newNotes = new HTTPRequests().getNotesFromProject(IDofProject, this.myUser);
		System.out.println(newNotes.size());
		for(Note n: newNotes) {
			notes.add(n);
			NoteList.addElement(n.getTitle());
		}
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
		refreshReminders();
		refreshProjectList();
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
	public Note getNoteAtIndex(int i) {
		return notes.get(i);
	}
	public Note getNoteFromDB(String noteID) {
		return new HTTPRequests().getANote(myUser, noteID);
	}
	public long getProjectIDFromIndex(int i) {
		return this.projects.get(i).getID();
	}
public static final String allnotesStr = "All Notes";

public void updateNote(Note updateThisNote) {
	new HTTPRequests().updateNote(updateThisNote, myUser);
}

public void createNewNote(Note newNote) {
	new HTTPRequests().addNote(newNote,myUser);
	
}

public void inserNewProject(String s) {
	new HTTPRequests().addProject(s,myUser);
	
}

public void deleteNote(Note noteToDelete) {
	new HTTPRequests().deleteNote(noteToDelete, myUser);
	
}

public void deleteProject(Long projectIdToDelete) {
	new HTTPRequests().deleteProject(projectIdToDelete, myUser);

}

public void shareProject(Long projectIDToShare, long userIdToShareWith) throws Exception{
	new HTTPRequests().shareProject(projectIDToShare, userIdToShareWith, myUser);
	
}

public long getUserIDFromStr(String userNameToShareWith) throws Exception {
	return new HTTPRequests().getUserID(userNameToShareWith);
}

public void shareNote(long noteID, long userIDToShareWith) throws Exception{
	new HTTPRequests().shareNote(noteID, userIDToShareWith, myUser);	
}

}
