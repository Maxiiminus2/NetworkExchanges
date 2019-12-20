package WindowsManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.*;

public class DefaultMemberDisplayPanel extends JPanel {
	
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;

	public DefaultMemberDisplayPanel(Window container) {
		
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel taLab = new JLabel("Tasks available");
		JButton registerButton = new JButton("Register");

		
		this.add(taLab);
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Name", "Service", "Contributors", "Volontary", "Estimated hours", "Estimated income"};
			Object[][] data = new Object[container.getSelectedNetwork().getTasks().size()][6];
			
			for (int i = 0 ; i < container.getSelectedNetwork().getTasks().size() ; i++) {
				Task t = container.getSelectedNetwork().getTasks().get(i);
				data[i][0] = t.getName();
				data[i][1] = t.getService().getName();
				data[i][2] = t.getContributorsNb() + "/" + t.getContributorsRequiredNb();
				data[i][3] = t.isVolontary() ? "Yes" : "No";
				data[i][4] = t.getEstimatedHours();
				data[i][5] = t.isVolontary() ? 0 : (t.getService().getHourlyCost() * t.getEstimatedHours());
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			registerButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						addContributor(container.getSelectedNetwork().getTasks().get(selectedRow));
					}
					setPanel("DMD");
					
				}
			});
		}
		
		
		
		
		this.add(registerButton);
		
		if(this.menuBar != null) {
			this.container.setJMenuBar(menuBar);
		}

		
	}

	public void setPanel(String string) {
		// TODO Auto-generated method stub
		this.container.changePanel(string);
	}

	public void addContributor(Task task) {
		// TODO Auto-generated method stub
		try {
			task.addContributor(this.userConnected);
		} catch (TaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
