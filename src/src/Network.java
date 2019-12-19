package src;
import java.util.ArrayList;

	public class Network {
		String name ;
		Admin admin;
		ArrayList<Member> member;
		ArrayList<Member> pendingMembers;
		ArrayList<Task> tasksAvailable;
		private final int password;
	
	public Network (Admin admin, String name ) {
		this.password = 100000 + (int)(Math.random() * ((999999 - 100000) + 1));
		this.name = name;
		this.member = new ArrayList<Member>();
		this.addMember(admin, this.password); // Un admin est �galement un membre.
		this.admin = admin;
		this.tasksAvailable = new ArrayList<Task>();
		this.pendingMembers = new ArrayList<Member>();
		// cr�� un mot de passe al�atoire, puis le communique � l'admin.
		admin.setNetworkPassword(this.password);
		
 }
	public String getNetworkName() {
		return this.name;
	}
	
	/**
	 * Ajoute un membre au r�seau.
	 * @param m
	 * @param password
	 */
	public void addMember (Member m, int password) {
		if (this.password == password) this.member.add(m);
	}
	
	public ArrayList<Member> getPendingMembers(){
		return this.pendingMembers;
	}
	
	public void addPendingMember(Member m) {
		this.pendingMembers.add(m);
	}
	
	private void removePendingMember(Member m) {
		this.pendingMembers.remove(m);
	}
	
	public Admin getAdmin() {
		return this.admin;
	}
	
	/**
	 * Retire un membre du r�seau.
	 * @param m
	 * @param password
	 */
	public void removeMember (Member m, int password) {
		if (this.password == password) this.member.remove(m);
	}

	/**
	 * Ajoute une t�che � faire au r�seau.
	 * @param t
	 */
	public void addTask(Task t) {
		this.tasksAvailable.add(t);		
	}
	
	public ArrayList<Task> getTasks() {
		return this.tasksAvailable;
	}
	
	public String toStringAvailableTasks() {
		String result = "";
		int nbTask = 1;
		for (Task t : this.tasksAvailable) {
			result += "T�che #" + nbTask + "\n";
			nbTask++;
			result += t.toString() + "\n" + "\n";
		}
		return result;
	}
	
	public ArrayList<Member> getNetworkMembers(){
		return this.member;
	}
	
}

