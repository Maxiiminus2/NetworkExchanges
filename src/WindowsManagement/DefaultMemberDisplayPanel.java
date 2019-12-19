package WindowsManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.*;

public class DefaultMemberDisplayPanel extends JPanel {
	
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;

	public DefaultMemberDisplayPanel(Window container) {
		
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		
			
		JMenuBar menu = new JMenuBar();
		
		JMenu profile = new JMenu("Profile");
		JMenu network = new JMenu("Network");
		
		menu.add(profile);
		menu.add(network);
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			if (container.getConnectedUser().equals(container.getSelectedNetwork().getAdmin())) {
				JMenu admin = new JMenu("Admin");
				JMenuItem adminValidateTask = new JMenuItem("Validate task");
				JMenuItem adminAddMember = new JMenuItem("Add member");
				JMenuItem adminRemoveMember = new JMenuItem("Remove member");
				
				admin.add(adminValidateTask);
				admin.add(adminAddMember);
				admin.add(adminRemoveMember);
				
				menu.add(admin);
			}
		}
		
		
		JMenuItem memberCreateNewTask = new JMenuItem("Create new task");
		
		network.add(memberCreateNewTask);
		
		JPanel panel = new JPanel();
		JLabel enterText = new JLabel("Enter text");
		JTextField tf = new JTextField(10);
		JButton send = new JButton("Send");
		JButton disconnectButton = new JButton("Disconnect");
		if (this.userConnected != null) {
			JLabel wallet = new JLabel("Wallet : " + this.userConnected.getWallet() + " tokens.");
			panel.add(wallet);
		}
		
		panel.add(enterText);
		panel.add(tf);
		panel.add(send);
		
		if (this.userConnected != null) {
			JLabel wallet = new JLabel("Wallet : " + this.userConnected.getWallet() + " tokens.");
			panel.add(wallet);
		}
		panel.add(disconnectButton);
		
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disconnect();
			}
		
		});
		JTextArea textArea = new JTextArea();
		
		
		this.add(BorderLayout.NORTH, menu);
		this.add(BorderLayout.SOUTH, panel);
		this.add(BorderLayout.CENTER, textArea);
		
	}
	
	public void setConnexionPanel() {
		this.container.changePanel("Connexion");
	}
	
	public void disconnect() {
		this.container.disconnectUserAndNetwork();
		this.setConnexionPanel();
	}
	
	
}
