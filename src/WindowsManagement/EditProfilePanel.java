package WindowsManagement;

import javax.swing.*;

import src.Member;
import src.MemberException;
import src.Network;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfilePanel extends JPanel {
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public EditProfilePanel(Window container) {
		
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel editProfile = new JPanel();
		GridLayout formLayout = new GridLayout(2,2);
		editProfile.setLayout(formLayout);
		formLayout.setHgap(15);
		JLabel usernameLabel = new JLabel("Username :");
		JButton usernameButton = new JButton("Change username");
		JLabel passwordLabel = new JLabel("Password : *******");
		JButton passwordButton = new JButton("Change password");
		
				
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
			usernameLabel = new JLabel("Username : " + container.getConnectedUser().getName());
			
			usernameButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String newUsername = JOptionPane.showInputDialog("New username : ");
					setUsername(newUsername);
					setPanel("EP");
					
				}
			});
			
			passwordButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String newPassword = JOptionPane.showInputDialog("New password : ");
					container.getConnectedUser().setPassword(newPassword);
					setPanel("EP");
				}
				
			});
			
			
		}
		
		editProfile.add(usernameLabel);
		editProfile.add(usernameButton);
		editProfile.add(passwordLabel);
		editProfile.add(passwordButton);
		
		this.add(editProfile);
				
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	
	public void setUsername(String newUsername) {
		
		boolean usernameAvailable = true;
		
		for (Member m : this.container.getMembers()) {
			usernameAvailable = usernameAvailable && !(m.getName().equals(newUsername));
		}
		
		if (usernameAvailable) {
			if(this.selectedNetwork.isAdmin(this.userConnected)) {
				System.out.println("OUI");
				this.selectedNetwork.getAdmin().setUsername(newUsername);
			}
			this.userConnected.setUsername(newUsername);
		}
		
		// ADMIN USERNAME
		
	}
}
