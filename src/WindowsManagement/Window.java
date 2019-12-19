package WindowsManagement;

import javax.swing.*;

import src.*;

import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {
	
	public static JLabel enterText;
	
	public ConnectingPanel connectPanel;
	public RegisterPanel regPanel;
	public CreateNetworkPanel cnPanel;
	
	private Network networkSelected;
	private Member connectedUser = null;
	private ArrayList<Member> members;
	private ArrayList<Network> networksAvailable = new ArrayList<Network>();
	
	public Window() {
		
		// CODE FINAL 
		
		networkSelected = null;
		connectedUser = null;
		//networksAvailable.add(new Network(new Admin("Bidule", 100), "network"));
		this.connectPanel = new ConnectingPanel(this.networksAvailable, this);
		this.regPanel = new RegisterPanel(this);
		this.cnPanel = new CreateNetworkPanel(this);
		
		this.members = new ArrayList<Member>();
		
		
		

		this.setContentPane(connectPanel);
		this.setVisible(true);
		
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
	
	public void changePanel(String pan) {
		
		this.getContentPane().removeAll();
		this.revalidate();
		this.repaint();
		
		this.updatePanels();
		
		if (pan.equals("Register")) this.setContentPane(this.regPanel);
		else if (pan.equals("Connexion")) this.setContentPane(this.connectPanel);
		else if (pan.contentEquals("CN")) this.setContentPane(this.cnPanel);
		this.revalidate();
		this.repaint();
		
		
		
		System.out.println(this.members.size());
		
	}
	
	public void addMember(Member m) {
		this.members.add(m);
	}
	
	public void updatePanels() {
		this.connectPanel = new ConnectingPanel(this.networksAvailable, this);
		this.regPanel = new RegisterPanel(this);
		this.cnPanel = new CreateNetworkPanel(this);
	}

	public ArrayList<Member> getMembers() {
		// TODO Auto-generated method stub
		return this.members;
	}
	
	public boolean passwordCorrect(String username, String password) {
		for(Member m : this.getMembers()) {
			if(m.getName().equals(username)) return m.getPassword().equals(password);
		}
		return false;
	}

	public void addNetwork(Network n) {
		// TODO Auto-generated method stub
		this.networksAvailable.add(n);
	}
	
}
