package projectWindow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.HTTPRequests;
import shared.DateUtil;
import shared.Note;
import shared.Project;
import shared.UserInfo;
import shared.UserSuite;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;

public class BigWindow {
	
	/** The following is test user Info**/

	private JFrame frmProductive;
	private UserSuite userSuite;
	private Note activeNote = null; 
	private long activeProjectID = 0;
	
public static void show(UserInfo userInfo) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				BigWindow window = new BigWindow(userInfo);
				window.frmProductive.setVisible(true);
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
		panel.add(UserName);
		
		this.UserName.setText(this.userSuite.getUserName());
		
		this.ReminderList.setModel(userSuite.refreshReminders());
		
		
		this.ProjectList.setModel(userSuite.refreshProjectList());
		long index = 0;
		
		this.NoteList.setModel(userSuite.refreshNoteList(index));
		
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
	private final JLabel noteCreatedDateLabel = new JLabel("");
	private final JLabel noteUpdatedLastDateLabel = new JLabel("");
	private final JTextField noteTitleField = new JTextField();
	private final JButton DeleteNote = new JButton("Note -");
	private final JButton DeleteProject = new JButton("Project -");
	private final JButton ShareNote = new JButton("Share Note");
	private final JButton ShareProject = new JButton("Share Project");
	private final JButton LogoutButton = new JButton("Logout");
	private final JPanel MainPanel = new JPanel();
	private final JPanel MiscPanel = new JPanel();
	private final JPanel NotePanel = new JPanel();
	private final JPanel ProgramOptionsPanel = new JPanel();
	private final JPanel ReminderPanel = new JPanel();
	private final JPanel ReminderOptions = new JPanel();
	private final JPanel ProjectPanel = new JPanel();
	private final JPanel ProjectOptionsPanel = new JPanel();
	private final JLabel ProjectLabel = new JLabel("Projects:");
	private final JLabel ReminderLabel = new JLabel("Reminders:\n");
	private final JPanel NoteOptionsPanel = new JPanel();
	private final JLabel NotesList = new JLabel("Notes:\n");
	private final JPanel DatePanel = new JPanel();
	private final JLabel CreatedAtLabel = new JLabel("Created At:");
	private final JLabel UpdatedLast = new JLabel("Updated Last:\n");
	private final JPanel titlePanel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("<html><b>Title:</b></html>");
	private final JPanel ReminderTitlePanel = new JPanel();
	private final JPanel ProjectTitlePanel = new JPanel();
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final JPanel NoteListTitle = new JPanel();
	private final JPanel BodyLabelPanel = new JPanel();
	private final JLabel BodyLabel = new JLabel("Body:");
	private final JButton RefreshButton = new JButton("Refresh");
	private final JButton StatsButton = new JButton("Stats");
	private final JPanel panel = new JPanel();
	private final JButton HelpButton = new JButton("Help");

	private void initialize() {
		ReminderList.setFixedCellHeight(40);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		titlePanel.add(lblNewLabel);
		titlePanel.add(noteTitleField);
		noteTitleField.setColumns(10);
		frmProductive = new JFrame();
		frmProductive.setTitle("Productive");
		frmProductive.setBounds(100, 100, 800, 450);
		frmProductive.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProductive.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		frmProductive.getContentPane().add(MainPanel);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.X_AXIS));
		MainPanel.add(MiscPanel);
		MiscPanel.setLayout(new BoxLayout(MiscPanel, BoxLayout.Y_AXIS));
		MainPanel.add(horizontalStrut);
		MainPanel.add(NotePanel);
		NotePanel.setLayout(new BoxLayout(NotePanel, BoxLayout.Y_AXIS));
		NoteListTitle.add(NotesList);
		NotesList.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		
		MiscPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		ProgramOptionsPanel.setBackground(Color.LIGHT_GRAY);
		MiscPanel.add(ProgramOptionsPanel);
		ProgramOptionsPanel.setLayout(new BoxLayout(ProgramOptionsPanel, BoxLayout.X_AXIS));
		ProgramOptionsPanel.add(LogoutButton);	
		
		ProgramOptionsPanel.add(RefreshButton);
		
		ProgramOptionsPanel.add(HelpButton);
		
		ProgramOptionsPanel.add(StatsButton);
		MiscPanel.add(ReminderPanel);
		ReminderPanel.setLayout(new BoxLayout(ReminderPanel, BoxLayout.Y_AXIS));
		ReminderPanel.add(ReminderTitlePanel);
		ReminderTitlePanel.setLayout(new BoxLayout(ReminderTitlePanel, BoxLayout.X_AXIS));
		ReminderTitlePanel.add(ReminderLabel);
		ReminderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ReminderLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		ReminderPanel.add(ReminderOptions);
		ReminderOptions.setLayout(new BoxLayout(ReminderOptions, BoxLayout.X_AXIS));
		ReminderPanel.add(ReminderList);
		MiscPanel.add(ProjectPanel);
		ProjectPanel.setLayout(new BoxLayout(ProjectPanel, BoxLayout.Y_AXIS));
		ProjectPanel.add(ProjectTitlePanel);
		ProjectTitlePanel.setLayout(new BoxLayout(ProjectTitlePanel, BoxLayout.X_AXIS));
		ProjectTitlePanel.add(ProjectLabel);
		ProjectLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		ProjectPanel.add(ProjectOptionsPanel);
		ProjectOptionsPanel.setLayout(new BoxLayout(ProjectOptionsPanel, BoxLayout.X_AXIS));
		ProjectPanel.add(ProjectList);
		NotePanel.add(NoteListTitle);
		NoteListTitle.setLayout(new BoxLayout(NoteListTitle, BoxLayout.X_AXIS));
		NotePanel.add(NoteOptionsPanel);
		NoteOptionsPanel.setLayout(new BoxLayout(NoteOptionsPanel, BoxLayout.X_AXIS));
		NotePanel.add(NoteList);
		NotePanel.add(titlePanel);
		NotePanel.add(BodyLabelPanel);
		BodyLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		BodyLabelPanel.add(BodyLabel);
		noteEditor.setWrapStyleWord(true);
		noteEditor.setFont(new Font("Arial", Font.PLAIN, 15));
		noteEditor.setLineWrap(true);
		noteEditor.setColumns(20);
		noteEditor.setRows(10);
		NotePanel.add(noteEditor);
		NotePanel.add(DatePanel);
		DatePanel.setLayout(new BoxLayout(DatePanel, BoxLayout.Y_AXIS));
		CreatedAtLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		DatePanel.add(CreatedAtLabel);
		DatePanel.add(noteCreatedDateLabel);
		UpdatedLast.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		DatePanel.add(UpdatedLast);
		DatePanel.add(noteUpdatedLastDateLabel);
		
		addActionListeners();
	}
	private void addActionListeners() {
		ReminderOptions.add(MakeReminder);

		MakeReminder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String)JOptionPane.showInputDialog(
	                    frmProductive,
	                    "New Reminder?",
	                    JOptionPane.PLAIN_MESSAGE);
				if (s==null||s.equals(""))
					return;
				userSuite.insertNewReminder(s);
				userSuite.refreshReminders();
			}
			
			
		});
		ReminderOptions.add(DeleteReminder);
		DeleteReminder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int i = ReminderList.getSelectedIndex();
				if(i>-1) {
					userSuite.deleteReminder(i);
				} else {
					warn("Please select a reminder before trying to delete it");
				}
				
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
		NoteOptionsPanel.add(MakeNote);
		NoteOptionsPanel.add(DeleteNote);
		NoteOptionsPanel.add(SaveButton);
		NoteOptionsPanel.add(ShareNote);
		ShareNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long noteID = getSelectedNoteID();
				long userIDToShareWith = -1;
				noteID = getSelectedNoteID();
				System.out.println("noteIDIS: " +noteID);
				if (noteID < 0) {
					warn("Please select a note before trying to share it.");
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
	                    frmProductive,
	                    AllStr.PROMPT_NAME_TO_SHARE_NOTE_WTIH,
	                    JOptionPane.PLAIN_MESSAGE);
				return s;
			}
		});
		SaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(activeNote == null) {
					warn("Please use the Note+ button before trying to save,\n"
							+ "or select an exisiting note from the notelist.");
					return;
				}
				
				if(noteTitleField.getText()==null||noteTitleField.getText().equals("")) {
					warn("You need a title before you save a note");
					return;
				}
				// IS this a new note
				if(activeNote.isNew() && isFormatGood()) {
					activeNote.setBody(noteEditor.getText());
					activeNote.setTitle(noteTitleField.getText());
					if(getSelectedProjectProperID()==(Project.NO_PROJECT_ID)) 
						activeNote.setProjectID(0);
					else 
						activeNote.setProjectID(getSelectedProjectProperID());
					userSuite.createNewNote(activeNote);
					updateModel();
					// Or is it an old note
				}else if (isFormatGood()){
					activeNote.setBody(noteEditor.getText());
					userSuite.updateNote(activeNote);
					updateModel();
				} else {
					warn("Incorrect Format");
					return;
				}
				warn("Note Saved!");
			}
			
		});
		DeleteNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Note noteToDelete;
				if(NoteList.getSelectedValue()==null)
				{
					warn("Please select a note before trying to delete.");
					return;
				}
					noteToDelete = userSuite.getNoteAtIndex(NoteList.getSelectedIndex());
					userSuite.deleteNote(noteToDelete);
					updateNoteModel();
				
				
			}
		});
		MakeNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				activeNote = new Note();
				noteEditor.setText("New Body");
				noteTitleField.setText("New Note");
				
				
				
			}
			
		});
		ProjectOptionsPanel.add(MakeProject);
		MakeProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					String s = (String)JOptionPane.showInputDialog(
		                    frmProductive,
		                    "New Project?",
		                    JOptionPane.PLAIN_MESSAGE);
					if (s==null||s.equals(""))
						return;
					userSuite.inserNewProject(s);
					userSuite.refreshProjectList();
				
			}
		});
		ProjectOptionsPanel.add(DeleteProject);
		DeleteProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Long ProjectIdToDelete = getSelectedProjectProperID();
				if (ProjectIdToDelete==Project.NO_PROJECT_ID)
					warn("Please select a project besides All Notes before deleting.");
				else {
					userSuite.deleteProject(ProjectIdToDelete);
					updateModel();
				}
				
				
			}
		});
		ProjectOptionsPanel.add(ShareProject);
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
	                    frmProductive,
	                    AllStr.PROMPT_NAME_TO_SHARE_PROJECT_WTIH,
	                    JOptionPane.PLAIN_MESSAGE);
				return s;
			}
		});	
		LogoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		RefreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateModel();
				
			}
		});
		StatsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String strToShow = "Could not get stats";
				try {
					 strToShow = new HTTPRequests().getStats();
				} catch (Exception es) {
					strToShow = "Could not get stats";
				}
				warn(strToShow);
	
				
				
			}
		});
		HelpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				warn(AllStr.HELP_NOTE);
				
			}
		});
	}
	private boolean isFormatGood() {
		if (this.noteTitleField==null||this.noteTitleField.equals(""))
			return false;
		if (this.noteEditor.getText()==null||this.noteEditor.getText().equals(""))
			return false;
		/*
		if (this.ProjectList.getSelectedValue()==null)
			return false;
		*/
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
