package WindowsManagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import src.Member;
import src.Network;
import src.Service;
import src.Task;

public class CreateTaskPanel extends JPanel {
	
	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public CreateTaskPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel taskForm = new JPanel();
		GridLayout formLayout = new GridLayout(3, 4);
		taskForm.setLayout(formLayout);
		formLayout.setHgap(15);
		JLabel nameLabel = new JLabel("Name : ");
		JLabel serviceLabel = new JLabel("Service : ");
		JLabel hoursExpectedLabel = new JLabel("Hours expected : ");
		JLabel contributorsNbLabel = new JLabel("Contributors number : ");
		JLabel volontaryLabel = new JLabel("Volontary : ");
		JTextField nameTF = new JTextField();
		String[] serviceList;
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		JButton createButton = new JButton("Create");
		JButton cancelButton = new JButton("Cancel");
		
		if (container.getSelectedNetwork() != null) {
			serviceList = new String[container.getSelectedNetwork().getServices().size()];
			for (int i = 0 ; i < container.getSelectedNetwork().getServices().size() ; i++) {
				serviceList[i] = container.getSelectedNetwork().getServices().get(i).getName();
			}
			JComboBox serviceTF = new JComboBox(serviceList);
			JTextField hoursExpectedTF = new JTextField();
			JTextField contributorsNbTF = new JTextField();
			String[] yesList = {"Yes", "No"};
			JComboBox volontaryTF = new JComboBox(yesList);
			
			
			taskForm.add(nameLabel);
			taskForm.add(nameTF);
			taskForm.add(serviceLabel);
			taskForm.add(serviceTF);
			taskForm.add(hoursExpectedLabel);
			taskForm.add(hoursExpectedTF);
			taskForm.add(contributorsNbLabel);
			taskForm.add(contributorsNbTF);
			taskForm.add(volontaryLabel);
			taskForm.add(volontaryTF);
			
			createButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(isInteger(contributorsNbTF.getText()) && isInteger(hoursExpectedTF.getText()) && serviceTF.getSelectedItem() != null
							&& Integer.parseInt(contributorsNbTF.getText()) > 0 && Integer.parseInt(hoursExpectedTF.getText()) > 0 && nameTF.getText() != null) {
						
						container.getConnectedUser().addTask(container.getSelectedNetwork(), nameTF.getText(), Integer.parseInt(contributorsNbTF.getText()), (( (String) volontaryTF.getSelectedItem()).equals("Yes") ? true : false) , container.getSelectedNetwork().getServices().get(serviceTF.getSelectedIndex()), Integer.parseInt(hoursExpectedTF.getText()));
						setPanel("DMD");
					}
					
					
				}
				
			});
		}
		
		
		
		
		
		
		
		buttonsPanel.add(createButton);
		buttonsPanel.add(cancelButton);
		
		this.add(taskForm);
		this.add(buttonsPanel);
		
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPanel("DMD");	
			}
			
		});
		
		
		
		
		
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	
	public boolean nameAvailable(String s) {
		
		for (Task t : this.selectedNetwork.getAllTasks()) {
			if (t.getName().equals(s)) return false;
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

}
