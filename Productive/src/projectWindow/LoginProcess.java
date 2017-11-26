package projectWindow;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import backend.HTTPRequests;
import shared.UserInfo;

public class LoginProcess {

	public LoginProcess() {
		// TODO Auto-generated constructor stub
	}
	public void runLoginProcess() throws InvocationTargetException, InterruptedException 
	{
		 EventQueue.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				showMainWindow();
				
			}

		 });

	}
	public static void main(String[] args) {
		LoginProcess l = new LoginProcess();
		try {
			l.runLoginProcess();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	private void showMainWindow() {
		Object[] options = {"Login","Create User!"};
		int n = JOptionPane.showOptionDialog(new Frame(),
		"Would You Like To Login Or Create An Account",
		"Productive",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,     //do not use a custom Icon
		options,  //the titles of buttons
		options[0]); //default button title
		if (n==0) {
			System.out.println("Showing login window");
			showLoginWindow();
		} else {
			showCreateUserWindow();
		}

	}
	private void showCreateUserWindow() {
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
        
        
        
        panel.add(new JLabel("email"));
        panel.add(field1);
        panel.add(new JLabel("password"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Account",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		UserInfo myUser = new UserInfo(field1.getText(),field2.getText());
        		tryToCreateAccount(myUser);
        } else {
        	System.exit(0);
        }
    }
	private void showLoginWindow() {
		JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        panel.add(new JLabel("email"));
        panel.add(field1);
        
        
        panel.add(new JLabel("password"));
        panel.add(field2);

        // test stuff
        field1.setText("fred@gmail.com");
        field2.setText("ser derp");
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Login",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
        		UserInfo myUser = new UserInfo(field1.getText(),field2.getText());
        		UserInfo userToUse = null;
        		try {
      			userToUse = new HTTPRequests().checkUserInfo(myUser);

      		} catch (Exception e) {
      			showLoginWindow();
      		}
        		if (userToUse == null) {
        			showLoginWindow();
        		}
        		BigWindow.show(userToUse);
        } else {
        	System.exit(0);
        }
		
	}
	// TODO: actually check the password
	
	private void tryToCreateAccount(UserInfo userInfo) {
		try {
			  new HTTPRequests().createAccount(userInfo);
		} catch (Exception e) {
			showMainWindow();
		}	
		showMainWindow();
	}
	
}
