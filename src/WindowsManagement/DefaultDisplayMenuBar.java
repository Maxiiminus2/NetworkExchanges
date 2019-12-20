package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import src.*;

public class DefaultDisplayMenuBar extends JMenuBar {
	
	private Network selectedNetwork;
	private Member connectedUser;
	private Window container;

	public DefaultDisplayMenuBar(Network n, Member m, Window w) {
		this.selectedNetwork = n;
		this.connectedUser = m;
		this.container = w;
		
		JMenu profile = new JMenu("Profile");
		JMenuItem depositMoney = new JMenuItem("Deposit money");
		JMenuItem withdrawMoney = new JMenuItem("Withdraw money");
		JMenuItem editProfile = new JMenuItem("Edit profile");
		JMenuItem manageServices = new JMenuItem("Manage services");
		profile.add(depositMoney);
		profile.add(withdrawMoney);
		profile.addSeparator();
		profile.add(editProfile);
		profile.add(manageServices);
		
		JLabel wallet = new JLabel("Wallet : " + this.connectedUser.getWallet() + " tokens");

		
		depositMoney.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String amount = JOptionPane.showInputDialog("Deposit amount : ");
				if (isInteger(amount) && Integer.parseInt(amount) >= 0) {
					deposit(Integer.parseInt(amount));
					// On aurait 
					wallet.setText("Wallet : " + m.getWallet() + " tokens");
				}
				
			}
			
		});
		
		withdrawMoney.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String amount = JOptionPane.showInputDialog("Withdrawal amount : ");
				if (isInteger(amount) && Integer.parseInt(amount) >= 0) {
					withdraw(Integer.parseInt(amount));
					wallet.setText("Wallet : " + m.getWallet() + " tokens");
				}
			}
		});
		
		editProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("EP");
			}
			
		});
		
		JMenu network = new JMenu("Network");
		JMenuItem tasksToDo = new JMenuItem("Tasks to do");
		JMenuItem tasksBeneficiary = new JMenuItem("Tasks beneficiary");
		JMenuItem createTask = new JMenuItem("Create task");
		JMenuItem availableTasks = new JMenuItem("Available tasks");
		network.add(tasksToDo);
		network.add(tasksBeneficiary);
		network.add(createTask);
		network.add(availableTasks);
		
		availableTasks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("DMD"); 
			}
		});
		
		manageServices.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("PMS");
			}
		});
		
		createTask.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("CT");
			}
		});
		
		
		
		this.add(profile);
		this.add(network);
		
		// Si le membre connecté est un administrateur.
		if (m.equals(n.getAdmin())) {
			
			JMenu admin = new JMenu("Admin");
			
			JMenuItem adminAddMember = new JMenuItem("Pending members");
			JMenuItem adminRemoveMember = new JMenuItem("Remove members");
			JMenuItem adminValidateTask = new JMenuItem("Validate tasks");
			JMenuItem adminManageServices = new JMenuItem("Manage services");
			JMenuItem adminManageReductions	= new JMenuItem("Manage reductions");
			
			admin.add(adminValidateTask);
			admin.addSeparator();
			admin.add(adminAddMember);
			admin.add(adminRemoveMember);
			admin.addSeparator();
			admin.add(adminManageServices);
			admin.add(adminManageReductions);
			
			this.add(admin);
			
			adminAddMember.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel("PM");
				}
			});
			
			adminManageServices.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel("MS");
				}
			});
			
			adminManageReductions.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel("MR");
				}
			});
			
			adminRemoveMember.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel("RM");
				}
			});
			
			
			
			
		}
		
		this.add(Box.createHorizontalStrut(650));
		
		this.add(wallet);
		
		this.add(Box.createHorizontalStrut(10));
		
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disconnect();
			}
		
		});
		this.add(disconnectButton);
		
		// Gestion des événements
		
		
		
		
		

	}

	public void withdraw(int amount) {
		// TODO Auto-generated method stub
		try {
			this.container.getConnectedUser().withdrawMoney(amount);
		} catch (MemberException e) {
			e.printStackTrace();
		}
	}

	public void deposit(int amount) {
		// TODO Auto-generated method stub
		this.container.getConnectedUser().depositMoney(amount);
	}

	public void setPanel(String panel) {
		// TODO Auto-generated method stub
		this.container.changePanel(panel);
		
	}

	public void setConnexionPanel() {
		this.container.changePanel("Connexion");
	}
	
	public void disconnect() {
		this.container.disconnectUserAndNetwork();
		this.container.setJMenuBar(null);
		this.setConnexionPanel();
	}
	
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
