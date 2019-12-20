package WindowsManagement;

import src.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConnectingPanel extends JPanel {
	
	public Window container;
	
	public ConnectingPanel(ArrayList<Network> networksAvailable, Window f) {

		this.container = f;
		
		// Initialisation des panels.
		JPanel panelSaisie = new JPanel();
		this.setSize(1080, 720);
		
		
		// Initialisation des composants
		JTextField username = new JTextField(10);
		JLabel usernameLabel = new JLabel("Username : ");
		JPasswordField password = new JPasswordField(10);
		JLabel passwordLabel = new JLabel("Password : ");
		
		String[] networkNameList = new String[networksAvailable.size()];
		for (int i = 0 ; i < networksAvailable.size() ; i++) {
			networkNameList[i] = networksAvailable.get(i).getNetworkName();
		}
		
		JComboBox networkList = new JComboBox(networkNameList);
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
		if(!(networksAvailable.size()>0)) connect.setEnabled(false);
		JButton register = new JButton("Register");
		JButton createNetwork = new JButton("Create network");
		if(!(this.container.getMembers().size()>0)) createNetwork.setEnabled(false);
		
		Box buttonsBox = Box.createHorizontalBox();
		
		buttonsBox.add(connect);
		buttonsBox.add(register);
		buttonsBox.add(createNetwork);
		
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Utiliser une méthode checkPassword dans Member à la place.
				if(passwordCorrect(username.getText(), password.getText())) {
					System.out.println("GOOD");
					if (isNetworkMember(username.getText(), (Network) networksAvailable.get(networkList.getSelectedIndex()))) {
						setConnectedUserAndNetwork(username.getText(), (Network) networksAvailable.get(networkList.getSelectedIndex()));
						setDefaultMemberDisplayPanel();
					} else {
						System.out.println("Membre absent.");
						if(isMemberPending(username.getText(), (Network) networksAvailable.get(networkList.getSelectedIndex()))) {
							System.out.println("Votre demande d'adhésion est toujours en attente.");
							JOptionPane affichage = new JOptionPane();
							affichage.showMessageDialog(null, "Demande d'adhésion en attente", "Information", JOptionPane.INFORMATION_MESSAGE);
						} else {
							addPendingMember(username.getText(),(Network) networksAvailable.get(networkList.getSelectedIndex()));
							System.out.println("Votre demande d'adhésion a bien été prise en compte.");
							JOptionPane affichage = new JOptionPane();
							affichage.showMessageDialog(null, "Demande d'adhésion envoyée à l'administrateur", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
						// Ajoute le membre dans la liste d'attente.
					}	
				} else {
					JOptionPane affichage = new JOptionPane();
					affichage.showMessageDialog(null, "Wrong password", "Warning", JOptionPane.WARNING_MESSAGE);

				}
			}
		});
		
		
		register.addActionListener(new ActionListener() {
			public void MousePressed() {
				setRegisterPanel();
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setRegisterPanel();
				
			}
		});
		
		createNetwork.addActionListener(new ActionListener() {
			public void MousePressed() {
				setCreateNetworkPanel();
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setCreateNetworkPanel();
				
			}
		});
		
		
		
		GridLayout globalLayout = new GridLayout(2,0);
		this.setLayout(globalLayout);
		
		this.add(panelSaisie);
		this.add(buttonsBox);
		
	}
	
	/**
	 * Ajoute un membre en attente de validation de l'administrateur dans un réseau.
	 * @param username
	 * @param n
	 */
	public void addPendingMember(String username, Network n) {
		// TODO Auto-generated method stub
		n.addPendingMember(this.getMember(username));
		
		
	}

	/**
	 * Détermine si un utilisateur est déjà en attente que l'administrateur valide son adhésion au réseau.
	 * @param username
	 * @param n
	 * @return
	 */
	public boolean isMemberPending(String username, Network n) {
		
		for (Member m : n.getPendingMembers()) {
			if (m.getName().contentEquals(username)) return true;
		}
		return false;
	}

	public void setRegisterPanel() {
		this.container.changePanel("Register");
	}
	
	public void setCreateNetworkPanel() {
		this.container.changePanel("CN");
	}
	
	public void setDefaultMemberDisplayPanel() {
		this.container.changePanel("DMD");
	}
	
	public boolean passwordCorrect(String username, String password) {
		return this.getMember(username).checkPassword(password);
	}
	
	private void setConnectedUserAndNetwork(String username, Object network) {
		Member m = this.getMember(username);
		this.container.setConnectedUserAndNetwork(m, (Network) network);
	}
	
	public Member getMember(String memberName) {
		
		for (Member m : this.container.getMembers()) {
			if(m.getName().equals(memberName)) return m;
		}
		return null;
	}
	
	public boolean isNetworkMember(String memberName, Network n) {
		
		for (Member m : n.getNetworkMembers()) {
			if(m.getName().equals(memberName)) return true;
		}
		return false;
		
	}
}
