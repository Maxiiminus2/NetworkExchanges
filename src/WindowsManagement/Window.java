package WindowsManagement;

import javax.swing.*;

import src.*;

import java.awt.*;
import java.util.ArrayList;

public class Window {
	
	public static JLabel enterText;
	
	public static void main(String[] args) {
		
		// CODE FINAL 
		
		Network networkSelected = null;
		Member connectedUser = null;
		ArrayList<Network> networksAvailable = new ArrayList<Network>();
		//networksAvailable.add(new Network(new Admin("Bidule", 100), "network"));
		
		ConnectingPanel connectPanel = new ConnectingPanel(networksAvailable);
		RegisterPanel registerPanel = new RegisterPanel();
		
		JFrame w = new JFrame("Network exchanges");
		w.setContentPane(connectPanel);
		
		/**
		JFrame frame = new JFrame("Network Exchanges");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080,720);
		
		JMenuBar menu = new JMenuBar();
		
		JMenu admin = new JMenu("Admin");
		JMenu network = new JMenu("Network");
		
		menu.add(admin);
		menu.add(network);
		
		JMenuItem adminValidateTask = new JMenuItem("Validate task");
		JMenuItem adminAddMember = new JMenuItem("Add member");
		JMenuItem adminRemoveMember = new JMenuItem("Remove member");
		
		admin.add(adminValidateTask);
		admin.add(adminAddMember);
		admin.add(adminRemoveMember);
		
		JMenuItem memberCreateNewTask = new JMenuItem("Create new task");
		
		network.add(memberCreateNewTask);
		
		JPanel panel = new JPanel();
		enterText = new JLabel("Enter text");
		JTextField tf = new JTextField(10);
		JButton send = new JButton("Send");
		JButton reset = new JButton("Reset");
		
		send.addActionListener(new ButtonListener());
		
		panel.add(enterText);
		panel.add(tf);
		panel.add(send);
		panel.add(reset);
		
		JTextArea textArea = new JTextArea();
		
		
		frame.add(BorderLayout.NORTH, menu);
		frame.add(BorderLayout.SOUTH, panel);
		frame.add(BorderLayout.CENTER, textArea);

		
		frame.setVisible(true);
		*/
	}
	
	private void changePanel(JPanel pan, JFrame f) {
		f.getContentPane().removeAll();
		f.setContentPane(pan);
		f.revalidate();
	}
	
}
