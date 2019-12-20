package WindowsManagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.*;

public class RemoveMembersPanel extends JPanel {

	private Member userConnected;
	private Network selectedNetwork;
	private Window container;
	private DefaultDisplayMenuBar menuBar = null;
	
	public RemoveMembersPanel(Window container) {
		this.userConnected = container.getConnectedUser();
		this.selectedNetwork = container.getSelectedNetwork();
		this.container = container;
		
		if (container.getConnectedUser() != null && container.getSelectedNetwork() != null) {
			menuBar = new DefaultDisplayMenuBar(container.getSelectedNetwork(), container.getConnectedUser(), container);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel msLabel = new JLabel("Network members");
		this.add(msLabel);
		
		JPanel lowPanel = new JPanel();
		
		JButton removeMember = new JButton("Remove");
		
		lowPanel.add(removeMember);
		
		
		if (this.selectedNetwork != null) {
			String colNames[] = {"Username", "Wallet"};
			Object[][] data = new Object[container.getSelectedNetwork().getNetworkMembers().size()][2];
			
			for (int i = 0 ; i < container.getSelectedNetwork().getNetworkMembers().size() ; i++) {
				data[i][0] = container.getSelectedNetwork().getNetworkMembers().get(i).getName();
				data[i][1] = container.getSelectedNetwork().getNetworkMembers().get(i).getWallet();
			}
			
			JTable table = new JTable(new DefaultTableModel(data, colNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			
			this.add(new JScrollPane(table));
			
			
			removeMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1 && !(container.getSelectedNetwork().getNetworkMembers().get(selectedRow).getName().equals(container.getSelectedNetwork().getAdmin().getName()))) {
						removeMember(container.getSelectedNetwork().getNetworkMembers().get(selectedRow));
					}
					setPanel("RM");
				}
			});
		}
		
		
		this.add(lowPanel);
	}
	
	public void removeMember(Member member) {
		// TODO Auto-generated method stub
		if (!(member.hasTasks())) {
			this.container.removeMember(member);

		} else {
			//Affichage.
			JOptionPane affichage = new JOptionPane();
			affichage.showMessageDialog(null, "Ce membre a des tâches en cours, il ne peut pas être supprimé", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	
	public void setPanel(String panel) {
		this.container.changePanel(panel);
	}
	

}
