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

import shared.Note;
import shared.UserInfo;
import shared.UserSuite;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;

public class BigWindow {
	
	/** The following is test user Info**/
	UserInfo testUser = new UserInfo("fred@gmail.com", "ser derp");

	private JFrame frame;
	private UserSuite userSuite;
	private Note activeNote = null; 
	
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
		this.UserName.setText(this.testUser.getUserEmail());
		this.ProjectList.setModel(userSuite.refreshProjectList());
		this.NoteList.setModel(userSuite.refreshNoteList(null));
		this.ReminderList.setModel(userSuite.refreshReminders());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JLabel UserName = new JLabel("UserName");
	JButton SaveButton = new JButton("Save");
	JButton MakeReminder = new JButton("Reminder +");
	JButton MakeNote = new JButton("Note +");
	JButton MakeProject = new JButton("Project+");
	JEditorPane editNotePane = new JEditorPane();
	JList<String> ReminderList = new JList();
	JList<String> NoteList = new JList();
	JList<String> ProjectList = new JList();
	private final JButton DeleteReminder = new JButton("Reminder -");

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		frame.getContentPane().add(UserName);
		
		frame.getContentPane().add(SaveButton);
		
		frame.getContentPane().add(MakeReminder);
		
		frame.getContentPane().add(DeleteReminder);
		
		frame.getContentPane().add(MakeNote);
		
		frame.getContentPane().add(MakeProject);
		
		frame.getContentPane().add(editNotePane);
		
		frame.getContentPane().add(ReminderList);
		
		frame.getContentPane().add(NoteList);
		
		frame.getContentPane().add(ProjectList);
		
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
		
	}
}
