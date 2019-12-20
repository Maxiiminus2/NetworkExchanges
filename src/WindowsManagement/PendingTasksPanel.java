package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import src.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PendingTasksPanel extends JPanel {
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public PendingTasksPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		JLabel pendingTasks = new JLabel("Pending tasks");
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(pendingTasks);
		
		JButton validateTask = new JButton("Validate");
		JButton deleteTask = new JButton("Delete");
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(validateTask);
		buttonsPanel.add(deleteTask);
		
		if(this.selectedNetwork != null) {
			String colNames[] = {"Name", "Service", "Contributors", "Volontary", "Estimated hours", "Status"};
			ArrayList<Task> pendingTasksArray = container.getSelectedNetwork().getPendingTasks();
			Object[][] data = new Object[pendingTasksArray.size()][6];
			
			for (int i = 0 ; i < pendingTasksArray.size() ; i++) {
				Task t = pendingTasksArray.get(i);
				
				data[i][0] = t.getName();
				data[i][1] = t.getService().getName();
				data[i][2] = t.getContributorsNb() + "/" + t.getContributorsRequiredNb();
				data[i][3] = t.isVolontary() ? "Yes" : "No";
				data[i][4] = t.getEstimatedHours();
				data[i][5] = t.getStatus();
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			deleteTask.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						Task t = pendingTasksArray.get(selectedRow);
						removeTask(t);
					}
					setPanel("PT");
				}
			});
			
			validateTask.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						Task t = pendingTasksArray.get(selectedRow);
						container.getSelectedNetwork().setTaskDone(t);
					}
					setPanel("PT");
				}
			});

		}
		
		this.add(buttonsPanel);
		
		
		
	}
	
	public void removeTask(Task t) {
		// TODO Auto-generated method stub
		t.getBeneficiary().removeBeneficiaryTask(t);
		if(this.selectedNetwork.isTaskAvailable(t)) this.selectedNetwork.deleteTaskAvailable(t);
		if(this.selectedNetwork.isPendingTask(t)) this.selectedNetwork.removePendingTask(t);

	}

	public void setPanel(String panel) {
		// TODO Auto-generated method stub
		this.container.changePanel(panel);
	}
}
