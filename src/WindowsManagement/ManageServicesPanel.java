package WindowsManagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.*;

public class ManageServicesPanel extends JPanel {

	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public ManageServicesPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel msLabel = new JLabel("Network services");
		this.add(msLabel);
		
		JPanel lowPanel = new JPanel();
		lowPanel.setLayout(new BoxLayout(lowPanel, BoxLayout.LINE_AXIS));
		JLabel serviceName = new JLabel("Service name : ");
		JTextField serviceNameTF = new JTextField();
		JLabel hourlyCost = new JLabel ("Hourly cost : ");
		JTextField hourlyCostTF = new JTextField();
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(2,2));
		formPanel.add(serviceName);
		formPanel.add(serviceNameTF);
		formPanel.add(hourlyCost);
		formPanel.add(hourlyCostTF);
		
		JButton addService = new JButton("Add");
		JButton removeService = new JButton("Remove");
		
		lowPanel.add(formPanel);
		lowPanel.add(addService);
		lowPanel.add(removeService);
		
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Service", "Hourly cost"};
			Object[][] data = new Object[container.getSelectedNetwork().getServices().size()][2];
			
			for (int i = 0 ; i < container.getSelectedNetwork().getServices().size() ; i++) {
				data[i][0] = container.getSelectedNetwork().getServices().get(i).getName();
				data[i][1] = container.getSelectedNetwork().getServices().get(i).getHourlyCost();
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			addService.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(serviceNameTF.getText() != null && isInteger(hourlyCostTF.getText()) && serviceNameAvailable(serviceNameTF.getText())) {
						addService(new Service(serviceNameTF.getText(), Integer.parseInt(hourlyCostTF.getText())));
						setPanel("MS");
					}
				}
			});
			
			removeService.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != 1) {
						removeService(container.getSelectedNetwork().getServices().get(selectedRow));
					}
					setPanel("MS");
				}
			});
		}
		
		
		this.add(lowPanel);
	}
	
	public boolean serviceNameAvailable(String text) {
		
		for (Service s : this.selectedNetwork.getServices()) {
			if (s.getName().equals(text)) return false;
		}
		
		return true;
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void addService(Service s) {
		this.selectedNetwork.getAdmin().addService(this.selectedNetwork, s);
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	
	public void removeService(Service s) {
		this.selectedNetwork.getAdmin().removeService(this.selectedNetwork, s);
	}

}
