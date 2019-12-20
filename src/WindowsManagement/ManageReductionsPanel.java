package WindowsManagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.*;

public class ManageReductionsPanel extends JPanel {

	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public ManageReductionsPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel msLabel = new JLabel("Network reductions");
		this.add(msLabel);
		
		JPanel lowPanel = new JPanel();
		lowPanel.setLayout(new BoxLayout(lowPanel, BoxLayout.LINE_AXIS));
		JLabel reductionName = new JLabel("Reduction name : ");
		JTextField reductionNameTF = new JTextField();
		JLabel reductionValue = new JLabel ("Reduction value (0-1) : ");
		JTextField reductionValueTF = new JTextField();
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(2,2));
		formPanel.add(reductionName);
		formPanel.add(reductionNameTF);
		formPanel.add(reductionValue);
		formPanel.add(reductionValueTF);
		
		JButton addReduction = new JButton("Add");
		JButton removeReduction = new JButton("Remove");
		
		lowPanel.add(formPanel);
		lowPanel.add(addReduction);
		lowPanel.add(removeReduction);
		
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Reduction", "Value"};
			Object[][] data = new Object[container.getSelectedNetwork().getReductions().size()][2];
			
			for (int i = 0 ; i < container.getSelectedNetwork().getReductions().size() ; i++) {
				data[i][0] = container.getSelectedNetwork().getReductions().get(i).getName();
				data[i][1] = container.getSelectedNetwork().getReductions().get(i).getReductionValue();
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			addReduction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(reductionNameTF.getText() != null && isDouble(reductionValueTF.getText()) && reductionNameAvailable(reductionNameTF.getText()) && reductionValueCorrect(reductionValueTF.getText())) {
						addReduction(new Reduction(reductionNameTF.getText(), Double.parseDouble(reductionValueTF.getText())));
						setPanel("MR");
					}
				}
			});
			
			removeReduction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1 && !(container.getSelectedNetwork().getReductions().get(selectedRow).getReductionValue() == 1.0)) {
						removeReduction(container.getSelectedNetwork().getReductions().get(selectedRow));
					}
					setPanel("MR");
				}
			});
		}
		
		
		this.add(lowPanel);
	}
	
	public boolean reductionValueCorrect(String text) {
		// TODO Auto-generated method stub
		double value = Double.parseDouble(text);
		for (Reduction r : this.selectedNetwork.getReductions()) {
			if(r.getReductionValue() == value) return false;
		}
		return value >= 0 && value <= 1;
		
	}

	public boolean reductionNameAvailable(String text) {
		
		for (Reduction s : this.selectedNetwork.getReductions()) {
			if (s.getName().equals(text)) return false;
		}
		
		return true;
	}

	public boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void addReduction(Reduction s) {
		this.selectedNetwork.getAdmin().addReduction(this.selectedNetwork, s);
	}
	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	
	public void removeReduction(Reduction s) {
		this.selectedNetwork.getAdmin().removeReduction(this.selectedNetwork, s);
	}
	
	public boolean enoughReductions() {
		return this.selectedNetwork.getReductions().size() > 1;
	}

}
