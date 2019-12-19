package WindowsManagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import src.*;

public class CreateNetworkPanel extends JPanel {
	
	private Window container;
	
	public CreateNetworkPanel(Window f) {
		
		this.container = f;
		
		JPanel panelSaisie = new JPanel();
		
		JTextField username = new JTextField(10);
		JLabel usernameLabel = new JLabel("Admin username : ");
		JPasswordField password = new JPasswordField(10);
		JLabel passwordLabel = new JLabel("Admin password : ");
		JLabel networkNameLabel = new JLabel("Network name : ");
		JTextField networkName = new JTextField(10);

		GridLayout saisieLayout = new GridLayout(3,2);
		saisieLayout.setVgap(5);
		panelSaisie.setLayout(saisieLayout);
		
		panelSaisie.setLayout(saisieLayout);
		panelSaisie.add(usernameLabel);
		panelSaisie.add(username);
		panelSaisie.add(passwordLabel);
		panelSaisie.add(password);
		panelSaisie.add(networkNameLabel);
		panelSaisie.add(networkName);
				
		JButton createNetworkButton = new JButton("CreateNetwork");
		
		
		createNetworkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(username.getText() != null && password.getText() != null && networkName.getText() != null){
					if(passwordCorrect(username.getText(), password.getText())) {
						Member adminM = getMember(username.getText());
						Admin admin = new Admin(adminM.getName(), adminM.getWallet(), adminM.getPassword());
						Network n = admin.createNetwork(networkName.getText());
						addNetwork(n);
						setConnexionPanel();
					}
				}
				
			}
			
		});
				
		GridLayout globalLayout = new GridLayout(2,1);
		globalLayout.setVgap(15);
		this.add(panelSaisie);
		this.add(createNetworkButton);
		this.setLayout(globalLayout);
		
		
		
	}
	
	public boolean passwordCorrect(String username, String password) {
		return this.container.passwordCorrect(username, password);
	}
	
	public Member getMember(String adminName) {
		
		for (Member m : this.container.getMembers()) {
			if(m.getName().equals(adminName)) return m;
		}
		return null;
	}
	
	public void setConnexionPanel() {
		this.container.changePanel("Connexion");
	}
	
	public void addNetwork(Network n) {
		System.out.println(n.getAdmin().getName());
		this.container.addNetwork(n);
	}
	
}
