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
	public DefaultMemberDisplayPanel dmdPanel;
	public PendingMembersPanel pendingMembersPanel;
	public ManageServicesPanel manageServicesPanel;
	public ManageReductionsPanel manageReductionsPanel;
	public RemoveMembersPanel removeMembersPanel;
	public MemberServicesManagementPanel msmPanel;
	public CreateTaskPanel createTaskPanel;
	public EditProfilePanel editProfilePanel;
	
	private Network networkSelected = null;
	private Member connectedUser = null;
	private ArrayList<Member> members = new ArrayList<Member>();
	private ArrayList<Network> networksAvailable = new ArrayList<Network>();
	
	public Window() {
		
		// CODE FINAL 

		//networksAvailable.add(new Network(new Admin("Bidule", 100), "network"));
		
		this.setSize(1080,720);
		this.setTitle("Network exchanges");
		this.setResizable(false);
		this.connectPanel = new ConnectingPanel(this.networksAvailable, this);
		this.regPanel = new RegisterPanel(this);
		this.cnPanel = new CreateNetworkPanel(this);
		this.dmdPanel = new DefaultMemberDisplayPanel(this);
		this.pendingMembersPanel = new PendingMembersPanel(this);
		this.manageServicesPanel = new ManageServicesPanel(this);
		this.manageReductionsPanel = new ManageReductionsPanel(this);
		this.removeMembersPanel = new RemoveMembersPanel(this);
		this.msmPanel = new MemberServicesManagementPanel(this);
		this.createTaskPanel = new CreateTaskPanel(this);
		this.editProfilePanel = new EditProfilePanel(this);
		
		this.members = new ArrayList<Member>();
		
		
		

		this.setContentPane(connectPanel);
		this.setVisible(true);
		
	}
	
	public void changePanel(String pan) {
		
		this.getContentPane().removeAll();
		this.revalidate();
		this.repaint();
		
		this.updatePanels();
		
		if (pan.equals("Register")) this.setContentPane(this.regPanel);
		else if (pan.equals("Connexion")) this.setContentPane(this.connectPanel);
		else if (pan.equals("CN")) this.setContentPane(this.cnPanel);
		else if (pan.equals("DMD")) this.setContentPane(this.dmdPanel);
		else if (pan.equals("PM")) this.setContentPane(this.pendingMembersPanel);
		else if (pan.equals("MS")) this.setContentPane(this.manageServicesPanel);
		else if (pan.equals("MR")) this.setContentPane(this.manageReductionsPanel);
		else if (pan.equals("RM")) this.setContentPane(this.removeMembersPanel);
		else if (pan.equals("PMS")) this.setContentPane(this.msmPanel);
		else if (pan.equals("CT")) this.setContentPane(this.createTaskPanel);
		else if (pan.equals("EP")) this.setContentPane(this.editProfilePanel);
		this.revalidate();
		this.repaint();
		
		
		
		System.out.println(this.members.size());
		
	}
	
	public void addMember(Member m) {
		this.members.add(m);
	}
	
	/** Mets à jour les panels avec les nouvelles données.
	 * 
	 */
	public void updatePanels() {
		this.connectPanel = new ConnectingPanel(this.networksAvailable, this);
		this.regPanel = new RegisterPanel(this);
		this.cnPanel = new CreateNetworkPanel(this);
		this.dmdPanel = new DefaultMemberDisplayPanel(this);
		this.pendingMembersPanel = new PendingMembersPanel(this);
		this.manageServicesPanel = new ManageServicesPanel(this);
		this.manageReductionsPanel = new ManageReductionsPanel(this);
		this.removeMembersPanel = new RemoveMembersPanel(this);
		this.msmPanel = new MemberServicesManagementPanel(this);
		this.createTaskPanel = new CreateTaskPanel(this);
		this.editProfilePanel = new EditProfilePanel(this);

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

	public void setConnectedUserAndNetwork(Member m, Network network) {
		// TODO Auto-generated method stub
		this.connectedUser = m;
		this.networkSelected = network;
	}
	
	public void disconnectUserAndNetwork() {
		this.connectedUser = null;
		this.networkSelected = null;
	}

	public Network getSelectedNetwork() {
		// TODO Auto-generated method stub
		return this.networkSelected;
	}
	
	public Member getConnectedUser() {
		return this.connectedUser;
	}

	public void removeMember(Member member) {
		// TODO Auto-generated method stub
		this.networkSelected.getAdmin().removeMember(this.networkSelected, member);
	}
	
}
