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
	private DefaultDisplayMenuBar menuBar = null;

	public DefaultMemberDisplayPanel(Window container) {
		
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
;
		
		JPanel panel = new JPanel();
		JLabel enterText = new JLabel("Enter text");
		JTextField tf = new JTextField(10);
		JButton send = new JButton("Send");
		
		
		panel.add(enterText);
		panel.add(tf);
		panel.add(send);
		
		
		JTextArea textArea = new JTextArea();
		
		if(this.menuBar != null) {
			this.container.setJMenuBar(menuBar);
		}
		this.add(BorderLayout.SOUTH, panel);
		this.add(BorderLayout.CENTER, textArea);
		
	}
	
}
