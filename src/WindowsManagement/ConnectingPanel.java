package WindowsManagement;

import src.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConnectingPanel extends JFrame {
	
	public ConnectingPanel(ArrayList<Network> networksAvailable) {

		// Initialisation des panels.
		JPanel panelSaisie = new JPanel();
		JPanel panelButtons = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1080, 720);
		
		
		// Initialisation des composants
		JTextField username = new JTextField(10);
		JLabel usernameLabel = new JLabel("Username : ");
		JTextField password = new JTextField(10);
		JLabel passwordLabel = new JLabel("Password : ");
		JComboBox networkList = new JComboBox(networksAvailable.toArray());
		JLabel networkListLabel = new JLabel("Network : ");
		
		//Layout de saisie
		
		GridLayout saisieLayout = new GridLayout(3,2);
		saisieLayout.setVgap(5);
		
		panelSaisie.setLayout(saisieLayout);
		panelSaisie.add(usernameLabel);
		panelSaisie.add(username);
		panelSaisie.add(passwordLabel);
		panelSaisie.add(password);
		panelSaisie.add(networkListLabel);
		panelSaisie.add(networkList);
		
		// Layout des boutons
		JButton connect = new JButton("Connect");
		if(!(networksAvailable.size()>0)) {
			connect.setEnabled(false);
		}
		JButton register = new JButton("Register");
		
		Box buttonsBox = Box.createHorizontalBox();
		
		buttonsBox.add(connect);
		buttonsBox.add(register);
		
	
		GridLayout globalLayout = new GridLayout(2,0);
		this.setLayout(globalLayout);
		
		this.add(panelSaisie);
		this.add(buttonsBox);

	}
}
