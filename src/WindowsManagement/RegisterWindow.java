package WindowsManagement;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {

	public RegisterWindow() {
		
		JPanel panelGlobal = new JPanel();
		JPanel panelSaisie = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1080, 720);
		
		JTextField username = new JTextField(10);
		JLabel usernameLabel = new JLabel("Username : ");
		JTextField password = new JTextField(10);
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
		
		GridLayout globalLayout = new GridLayout(2,1);
		globalLayout.setVgap(15);
		panelGlobal.add(panelSaisie);
		panelGlobal.add(registerButton);
		panelGlobal.setLayout(globalLayout);
		
		this.add(panelGlobal);
		
		
	}
}
