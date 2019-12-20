package src;
import java.util.ArrayList;

	public class Network {
		String name ;
		Admin admin;
		ArrayList<Member> member;
		ArrayList<Member> pendingMembers;
		ArrayList<Task> tasksAvailable;
		ArrayList<Service> services;
		ArrayList<Reduction> reductions;
		private final int password;
	
	public Network (Admin admin, String name ) {
		this.password = 100000 + (int)(Math.random() * ((999999 - 100000) + 1));
		this.name = name;
		this.member = new ArrayList<Member>();
		this.addMember(admin, this.password); // Un admin est également un membre.
		this.admin = admin;
		this.tasksAvailable = new ArrayList<Task>();
		this.pendingMembers = new ArrayList<Member>();
		this.services = new ArrayList<Service>();
		this.reductions = new ArrayList<Reduction>();
		reductions.add(new Reduction("default", 1));
		// créé un mot de passe aléatoire, puis le communique à l'admin.
		admin.setNetworkPassword(this.password);
		
		
 }
	public String getNetworkName() {
		return this.name;
	}
	
	/**
	 * Ajoute un membre au réseau.
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
	
	public boolean isAdmin(Member m) {
		return this.admin.getName().equals(m.getName());
	}
	
	/**
	 * Retire un membre du réseau.
	 * @param m
	 * @param password
	 */
	public void removeMember (Member m, int password) {
		if (this.password == password) this.member.remove(m);
	}

	/**
	 * Ajoute une tâche à faire au réseau.
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
			result += "Tâche #" + nbTask + "\n";
			nbTask++;
			result += t.toString() + "\n" + "\n";
		}
		return result;
	}
	
	public ArrayList<Member> getNetworkMembers(){
		return this.member;
	}
	
	public ArrayList<Service> getServices(){
		return this.services;
	}
	public void addService(Service s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) this.services.add(s);
	}
	public void removeService(Service s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) this.services.remove(s);
		
	}
	
	public ArrayList<Reduction> getReductions(){
		return this.reductions;
	}
	public void addReduction(Reduction s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) this.reductions.add(s);
	}
	public void removeReduction(Reduction s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) this.reductions.remove(s);
		
	}
}

