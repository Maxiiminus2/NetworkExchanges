package WindowsManagement;

import javax.swing.*;

import src.Member;
import src.MemberException;
import src.Network;
import src.Reduction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
		GridLayout formLayout = new GridLayout(3,2);
		editProfile.setLayout(formLayout);
		formLayout.setHgap(15);
		JLabel usernameLabel = new JLabel("Username :");
		JButton usernameButton = new JButton("Change username");
		JLabel passwordLabel = new JLabel("Password : *******");
		JButton passwordButton = new JButton("Change password");
		JLabel reductionLabel = new JLabel("Social reduction : ");
		
		
				
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			
			String[] reductionDisplayList = new String[this.selectedNetwork.getReductions().size()];
			int displayIndex = 0;
			for (int i = 0 ; i < this.selectedNetwork.getReductions().size() ; i++) {
				reductionDisplayList[i] = this.selectedNetwork.getReductions().get(i).getName() + " : " +  this.selectedNetwork.getReductions().get(i).getReductionValue();
				if(this.selectedNetwork.getReductions().get(i).getReductionValue() == this.userConnected.getReductionValue()) {
					displayIndex = i;
				}
			}
			
			JComboBox reductionList = new JComboBox(reductionDisplayList);
			reductionList.setSelectedItem(reductionDisplayList[displayIndex]);
			
			reductionList.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(e.getStateChange() == ItemEvent.SELECTED) {
						Reduction r = container.getSelectedNetwork().getReductions().get(reductionList.getSelectedIndex());
						container.getConnectedUser().setReduction(r);
					}
				}
				
			});
			
			editProfile.add(reductionLabel);
			editProfile.add(reductionList);
			
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
