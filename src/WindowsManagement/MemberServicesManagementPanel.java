package WindowsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.Member;
import src.Network;
import src.Service;

public class MemberServicesManagementPanel extends JPanel {
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public MemberServicesManagementPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel yrServices = new JLabel("Your services");
		JLabel avServices = new JLabel("Available services");
		JButton addService = new JButton("Add");
		JButton removeService = new JButton("Remove");
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(addService);
		buttonsPanel.add(removeService);
		
		this.add(yrServices);
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Service", "Hourly cost"};
			Object[][] dataMyServices = new Object[container.getConnectedUser().getServices().size()][2];
			Object[][] dataAvailableServices = new Object[container.getSelectedNetwork().getServices().size() - container.getConnectedUser().getServices().size()][2];
			
			for (int i = 0 ; i < container.getConnectedUser().getServices().size() ; i++) {
				dataMyServices[i][0] = container.getConnectedUser().getServices().get(i).getName();
				dataMyServices[i][1] = container.getConnectedUser().getServices().get(i).getHourlyCost();
			}
			
			int index = 0;
			int tableIndex = 0;
			while (index < container.getSelectedNetwork().getServices().size()) {
				
				if (!container.getConnectedUser().canDo(container.getSelectedNetwork().getServices().get(index))) {
					dataAvailableServices[tableIndex][0] = container.getSelectedNetwork().getServices().get(index).getName();
					dataAvailableServices[tableIndex][1] = container.getSelectedNetwork().getServices().get(index).getHourlyCost();
							
					tableIndex++;
				}
				index++;
			}
			
			JTable myServices = new JTable(new DefaultTableModel(dataMyServices, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			JTable networkOnlyServices = new JTable(new DefaultTableModel(dataAvailableServices, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(myServices));
			this.add(avServices);
			this.add(new JScrollPane(networkOnlyServices));
			
			addService.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = networkOnlyServices.getSelectedRow();
					if (selectedRow != -1) {
						Service s = findService((String) dataAvailableServices[selectedRow][0]);
						container.getConnectedUser().addService(s);
					}
					setPanel("PMS");
				}
			});
			
			removeService.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = myServices.getSelectedRow();
					if (selectedRow != -1) {
						container.getConnectedUser().removeService(container.getConnectedUser().getServices().get(selectedRow));
					}
					setPanel("PMS");
				}
			});
		
		}
		
		this.add(buttonsPanel);
		
	}
	
	public Service findService(String s) {
		
		for (Service service : this.selectedNetwork.getServices()) {
			if (service.getName().equals(s)) return service;
		}
		
		return null;
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
}
