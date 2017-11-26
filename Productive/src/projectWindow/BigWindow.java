package projectWindow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.DateUtil;
import shared.Note;
import shared.Project;
import shared.UserInfo;
import shared.UserSuite;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BigWindow {
	
	/** The following is test user Info**/
	UserInfo testUser = new UserInfo("fred@gmail.com", "ser derp");

	private JFrame frame;
	private UserSuite userSuite;
	private Note activeNote = null; 
	private long activeProjectID = 0;
	
public static void show(UserInfo userInfo) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				BigWindow window = new BigWindow(userInfo);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}

	/**
	 * Create the application.
	 */
	public BigWindow(UserInfo userInfo) {
		userSuite = new UserSuite(userInfo);
		initialize();
		updateModel();
	}
	
	public void updateModel() {
		activeNote = null;
		this.UserName.setText(this.testUser.getUserEmail());
		this.ProjectList.setModel(userSuite.refreshProjectList());
		long index = 0;
		this.NoteList.setModel(userSuite.refreshNoteList(index));
		this.ReminderList.setModel(userSuite.refreshReminders());
	}
	public void updateNoteModel() {
		this.NoteList.setModel(userSuite.refreshNoteList(activeProjectID));
	}
	
	private void updateNoteDisplay() {
		System.out.println("the new noteBody is:" + activeNote.getBody());
		this.noteEditor.setText(activeNote.getBody());
		this.noteTitleField.setText(activeNote.getTitle());
		this.noteCreatedDateLabel.setText(activeNote.getCreatedDate().toString());
		this.noteUpdatedLastDateLabel.setText(activeNote.getUpdatedLastDate().toString());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JLabel UserName = new JLabel("UserName");
	JButton SaveButton = new JButton("Save");
	JButton MakeReminder = new JButton("Reminder +");
	JButton MakeNote = new JButton("Note +");
	JButton MakeProject = new JButton("Project+");
	JList<String> ReminderList = new JList();
	JList<String> NoteList = new JList();
	JList<String> ProjectList = new JList();
	private final JButton DeleteReminder = new JButton("Reminder -");
	private final JTextArea noteEditor = new JTextArea();
	private final JLabel noteCreatedDateLabel = new JLabel("New label");
	private final JLabel noteUpdatedLastDateLabel = new JLabel("New label");
	private final JTextField noteTitleField = new JTextField();
	private final JButton DeleteNote = new JButton("Note -");
	private final JButton DeleteProject = new JButton("Project -");
	private final JButton ShareNote = new JButton("Share Note");
	private final JButton ShareProject = new JButton("Share Project");
	private final JButton LogoutButton = new JButton("Logout");

	private void initialize() {
		noteTitleField.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		frame.getContentPane().add(UserName);
		
		frame.getContentPane().add(LogoutButton);
		
		frame.getContentPane().add(SaveButton);
		
		frame.getContentPane().add(MakeReminder);
		
		frame.getContentPane().add(DeleteReminder);
		
		frame.getContentPane().add(MakeNote);
		
		frame.getContentPane().add(DeleteNote);
		
		frame.getContentPane().add(ShareNote);
		
		frame.getContentPane().add(MakeProject);
		
		frame.getContentPane().add(noteEditor);
		
		frame.getContentPane().add(DeleteProject);
		
		frame.getContentPane().add(ShareProject);
		
		frame.getContentPane().add(noteTitleField);
		
		frame.getContentPane().add(noteCreatedDateLabel);
		
		frame.getContentPane().add(noteUpdatedLastDateLabel);
		
		frame.getContentPane().add(ReminderList);
		
		frame.getContentPane().add(NoteList);
		
		frame.getContentPane().add(ProjectList);
		
		addActionListeners();
	}
	private void addActionListeners() {

		MakeReminder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    "New Reminder?",
	                    JOptionPane.PLAIN_MESSAGE);
				System.out.println(s);
				userSuite.insertNewReminder(s);
				userSuite.refreshReminders();
			}
			
			
		});
		DeleteReminder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ReminderList.getSelectedIndex();
				userSuite.deleteReminder(i);
				userSuite.refreshReminders();
			}
		});
		ProjectList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				activeProjectID = getSelectedProjectProperID();
				updateNoteModel();
			}
		});
		NoteList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (NoteList.getSelectedIndex()!=-1) {
					activeNote = userSuite.getNoteAtIndex(NoteList.getSelectedIndex());
					activeNote = userSuite.getNoteFromDB(activeNote.getNoteId());
					updateNoteDisplay();
				}
				
			}

		});
		SaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(activeNote !=null && activeNote.isNew() && isFormatGood()) {
					activeNote.setBody(noteEditor.getText());
					activeNote.setTitle(noteTitleField.getText());
					if(getSelectedProjectProperID()==(Project.NO_PROJECT_ID)) 
						activeNote.setProjectID(0);
					else 
						activeNote.setProjectID(getSelectedProjectProperID());
					userSuite.createNewNote(activeNote);
					updateModel();
				}else {
					activeNote.setBody(noteEditor.getText());
					userSuite.updateNote(activeNote);
					updateModel();
				}	
			}
		});
		MakeNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				activeNote = new Note();
				noteEditor.setText("Put your text here :)");
				
				
			}
			
		});
		MakeProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					String s = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "New Project?",
		                    JOptionPane.PLAIN_MESSAGE);
					System.out.println(s);
					userSuite.inserNewProject(s);
					userSuite.refreshProjectList();
				
			}
		});
		DeleteNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Note noteToDelete;
				if(NoteList.getSelectedValue()!=null)
				{
					noteToDelete = userSuite.getNoteAtIndex(NoteList.getSelectedIndex());
					userSuite.deleteNote(noteToDelete);
					updateNoteModel();
				}
				
			}
		});
		DeleteProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Long ProjectIdToDelete = getSelectedProjectProperID();
				if (ProjectIdToDelete==Project.NO_PROJECT_ID)
					warn("cannot delete this projectr");
				else {
					userSuite.deleteProject(ProjectIdToDelete);
					updateModel();
				}
				
				
			}
		});
		ShareProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long projectIDToShare = getSelectedProjectProperID();
				if (projectIDToShare==Project.NO_PROJECT_ID) {
					warn("All Notes Is Not A Project");
					return;
				} else {
					String userNameToShareWith = userToAdd();
					long userIDToShareWith = -1;
					try {
						 userIDToShareWith = userSuite.getUserIDFromStr(userNameToShareWith);
						 
					} catch (Exception ex) {
						warn(AllStr.UNKOWN_USERNAME);
						return;
					}
					
					try {
						
						userSuite.shareProject(projectIDToShare, userIDToShareWith);
					} catch (Exception ex) {
						warn(ex.getMessage());
					}
				}
				warn(AllStr.USER_SUCCESSFULLY_ADDED_TO_PROJECT);
			}
			private String userToAdd() {
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    AllStr.PROMPT_NAME_TO_SHARE_PROJECT_WTIH,
	                    JOptionPane.PLAIN_MESSAGE);
				return s;
			}
		});		
		ShareNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long noteID = getSelectedNoteID();
				long userIDToShareWith = -1;
				noteID = getSelectedNoteID();
				System.out.println("noteIDIS: " +noteID);
				if (noteID < 0) {
					warn(AllStr.INDEX_OUT_OF_BOUNDS);
					return;
				} else {
					String usernameToShareWith = userToAdd();
				
					try {
						 userIDToShareWith = userSuite.getUserIDFromStr(usernameToShareWith);
						 
					} catch (Exception ex) {
						warn(AllStr.UNKOWN_USERNAME);
						return;
					}
				}
				try {
					userSuite.shareNote(noteID, userIDToShareWith);
				} catch (Exception ex) {
					warn(ex.getMessage());
				}
			
			warn(AllStr.USER_SUCCESSFULLY_ADDED_TO_NOTE);
			}
			private String userToAdd() {
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    AllStr.PROMPT_NAME_TO_SHARE_NOTE_WTIH,
	                    JOptionPane.PLAIN_MESSAGE);
				return s;
			}
		});
	}
	private boolean isFormatGood() {
		if (this.noteTitleField==null||this.noteTitleField.equals(""))
			return false;
		if (this.noteEditor.getText()==null||this.noteEditor.getText().equals(""))
			return false;
		if (this.ProjectList.getSelectedValue()==null)
			return false;
		return true;
	}
	private long getSelectedProjectProperID() {
		if(userSuite.projects.size()==0)
			return 0;
		int i = this.ProjectList.getSelectedIndex();
		if (i < 0)
			return userSuite.getProjectIDFromIndex(0);
		else
			return userSuite.getProjectIDFromIndex(i);
	}
	private long getSelectedNoteID() {
		if(userSuite.NoteList.isEmpty())
			return -1;
		if(NoteList.isSelectionEmpty())
			return -1;
		return Long.parseLong(userSuite.getNoteAtIndex(NoteList.getSelectedIndex()).getNoteId());
	}
	private void warn() {
		warn("An error has occured");
	}
	private void warn(String strToShow) {
		JOptionPane.showMessageDialog(null, strToShow);
	}
}
