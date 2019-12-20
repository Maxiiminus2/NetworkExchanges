package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.*;

public class BeneficiaryTasksPanel extends JPanel {
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public BeneficiaryTasksPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel btLabel = new JLabel("Your beneficiary tasks");
		this.add(btLabel);
		JButton newButton = new JButton("New");
		JButton deleteButton = new JButton("Delete");
		JButton validateButton = new JButton("Validate");
		JButton payButton = new JButton("Pay");
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(newButton);
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(validateButton);
		buttonsPanel.add(payButton);
		
		if(this.selectedNetwork != null) {
			String colNames[] = {"Name", "Service", "Contributors", "Volontary", "Estimated hours", "Estimated price", "Status"};
			Object[][] data = new Object[this.userConnected.getBeneficiaryTaskNb(this.selectedNetwork)][7];
		
			ArrayList<Task> beneficiaryTasks = this.userConnected.getBeneficiaryTasksByNetwork(this.selectedNetwork);
			
			for(int i = 0 ; i < beneficiaryTasks.size() ; i++) {
				Task t = beneficiaryTasks.get(i);
				
				data[i][0] = t.getName();
				data[i][1] = t.getService().getName();
				data[i][2] = t.getContributorsNb() + "/" + t.getContributorsRequiredNb();
				data[i][3] = t.isVolontary() ? "Yes" : "No";
				data[i][4] = t.getEstimatedHours();
				data[i][5] = t.getEstimatedBeneficiaryPrice();
				data[i][6] = t.getStatus();
			}
			
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						Task t = container.getConnectedUser().getBeneficiaryTask((String) data[selectedRow][0]);
						if(t.getStatus().equals("Waiting for contributors") || t.getStatus().equals("Being done")) removeBeneficiaryTask(t);
					}
					setPanel("BT");
				}
			});
			
			validateButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						container.getConnectedUser().validateTask(beneficiaryTasks.get(selectedRow));
					}
					setPanel("BT");
				}
			});
			
			payButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						Task t = container.getConnectedUser().getBeneficiaryTask((String) data[selectedRow][0]);
						String hoursSpent = JOptionPane.showInputDialog("Hours spent : ");
						if (t.getStatus().equals("Waiting for payement") && isInteger(hoursSpent)) {
							try {
								container.getConnectedUser().setHoursSpent(Integer.parseInt(hoursSpent), t);
								container.getConnectedUser().payContributors(t);
								t.setTaskDone(container.getConnectedUser());
							} catch (MemberException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					setPanel("BT");
				}
			});
			
		}
		
		
		
		
		
		
		newButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("CT");
			}
		});
		
		this.add(buttonsPanel);
		
	}
	
	public void removeBeneficiaryTask(Task t) {
		// TODO Auto-generated method stub
		this.userConnected.removeBeneficiaryTask(t);
		if(this.selectedNetwork.isTaskAvailable(t)) this.selectedNetwork.deleteTaskAvailable(t);
		if(this.selectedNetwork.isPendingTask(t)) this.selectedNetwork.removePendingTask(t);

	}

	public void setPanel(String panel) {
		this.container.changePanel(panel);
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
