package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.*;

public class TasksToDoPanel extends JPanel{
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;

	public TasksToDoPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel btLabel = new JLabel("Your tasks to do");
		this.add(btLabel);
		JButton newButton = new JButton("Add");
		JButton deleteButton = new JButton("Unsubscribe");
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(newButton);
		buttonsPanel.add(deleteButton);
		
		if(this.selectedNetwork != null) {
			String colNames[] = {"Name", "Service", "Contributors", "Volontary", "Estimated hours", "Estimated income", "Status"};
			ArrayList<Task> tasksToDo = concatenateArrays(this.userConnected.getTasksSubscribed(), this.userConnected.getTasksToDo());
			Object[][] data = new Object[tasksToDo.size()][7];
			
			for (int i = 0 ; i < tasksToDo.size() ; i++) {
				Task t = tasksToDo.get(i);
				
				data[i][0] = t.getName();
				data[i][1] = t.getService().getName();
				data[i][2] = t.getContributorsNb() + "/" + t.getContributorsRequiredNb();
				data[i][3] = t.isVolontary() ? "Yes" : "No";
				data[i][4] = t.getEstimatedHours();
				data[i][5] = t.isVolontary() ? 0 : (t.getService().getHourlyCost() * t.getEstimatedHours()) / t.getContributorsRequiredNb();
				data[i][6] = t.getStatus();
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			newButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel("DMD");
				}
				
			});
			
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1 && tasksToDo.get(selectedRow).getStatus().equals("Waiting for contributors")) {
						removeContributor(tasksToDo.get(selectedRow));
					}
					
					setPanel("TP");
				}
			});
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		this.add(buttonsPanel);
	}

	public void removeContributor(Task t) {
		// TODO Auto-generated method stub
		t.removeContributor(this.userConnected);
		
	}

	public void setPanel(String panel) {
		// TODO Auto-generated method stub
		this.container.changePanel(panel);
	}

	private ArrayList<Task> concatenateArrays(ArrayList<Task> tasksSubscribed, ArrayList<Task> tasksToDo) {
		// TODO Auto-generated method stub
		ArrayList<Task> newArrayList = new ArrayList<Task>();
		
		for (Task t : tasksSubscribed) {
			newArrayList.add(t);
		}
		
		for (Task t : tasksToDo) {
			newArrayList.add(t);
		}
		
		return newArrayList;
	}
}
