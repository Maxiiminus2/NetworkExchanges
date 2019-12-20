package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import src.*;

public class PendingMembersPanel extends JPanel {
	
	private Member userConnected;
	private Network selectedNetwork;
	private ArrayList<Member> pendingMembers = null;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public PendingMembersPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (this.selectedNetwork != null) this.pendingMembers = container.getSelectedNetwork().getPendingMembers();
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel pmLabel = new JLabel("Pending members");
		this.add(pmLabel);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		JButton validateButton = new JButton("Validate");
		JButton deleteButton = new JButton("Delete");
		buttonPanel.add(validateButton);
		buttonPanel.add(deleteButton);
		
		
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Username", "Wallet"};
			Object[][] data = new Object[container.getSelectedNetwork().getPendingMembers().size()][2];
	
			for (int i = 0 ; i <  container.getSelectedNetwork().getPendingMembers().size() ; i++) {
				data[i][0] =  container.getSelectedNetwork().getPendingMembers().get(i).getName();
				data[i][1] =  container.getSelectedNetwork().getPendingMembers().get(i).getWallet();
			}
			
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			this.add(new JScrollPane(table));
			
			validateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						addMember(container.getSelectedNetwork().getPendingMembers().get(selectedRow));
					}
					setPanel("PM");
				}
			});
			
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						removePendingMember(container.getSelectedNetwork().getPendingMembers().get(selectedRow));

					}
					setPanel("PM");
				}
			});
		}
			
		
		
			
		
	
		
		
		
		
		
		this.add(buttonPanel);
		if(this.menuBar != null) {
		this.container.setJMenuBar(menuBar);
		}
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	
	public void removePendingMember(Member m) {
		this.selectedNetwork.getPendingMembers().remove(m);
		
		
	}
	
	public void addMember(Member m) {
		this.selectedNetwork.getAdmin().addMember(this.selectedNetwork, m);
		removePendingMember(m);
		
	}
	
}
