package WindowsManagement;

import javax.swing.*;

import src.Member;
import src.Service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {

	private Window container;
	
	public RegisterPanel(Window f) {
		
		this.container = f;
		
		JPanel panelSaisie = new JPanel();
		
		JTextField username = new JTextField(10);
		JLabel usernameLabel = new JLabel("Username : ");
		JPasswordField password = new JPasswordField(10);
		JLabel passwordLabel = new JLabel("Password : ");
		JLabel walletLabel = new JLabel("Wallet : ");
		JTextField wallet = new JTextField(10);

		GridLayout saisieLayout = new GridLayout(3,2);
		saisieLayout.setVgap(5);
		panelSaisie.setLayout(saisieLayout);
		
		panelSaisie.setLayout(saisieLayout);
		panelSaisie.add(usernameLabel);
		panelSaisie.add(username);
		panelSaisie.add(passwordLabel);
		panelSaisie.add(password);
		panelSaisie.add(walletLabel);
		panelSaisie.add(wallet);
				
		JButton registerButton = new JButton("Register");
		
		
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(username.getText() != null && password.getText() != null && isInteger(wallet.getText()) && usernameAvailable(username.getText())){
					Member m = new Member(username.getText(), Integer.parseInt(wallet.getText()), password.getText());
					addMember(m);
					setConnexionPanel();
				}
				
			}

			
			
		});
		
		GridLayout globalLayout = new GridLayout(2,1);
		globalLayout.setVgap(15);
		this.add(panelSaisie);
		this.add(registerButton);
		this.setLayout(globalLayout);
		
		
		
	}
	
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void addMember(Member m) {
		this.container.addMember(m);
	}
	
	public void setConnexionPanel() {
		this.container.changePanel("Connexion");
	}
	
	private boolean usernameAvailable(String text) {

		for (Member m : this.container.getMembers()) {
			if(m.getName().equals(text)) return false;
		}
		
		return true;
	}
}
