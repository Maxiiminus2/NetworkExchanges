package src;
import java.util.ArrayList;

	public class Network {
		private String name ;
		private Admin admin;
		private ArrayList<Member> member;
		private ArrayList<Member> pendingMembers;
		private ArrayList<Task> allTasks; // toutes les t�ches depuis la cr�ation du r�seau.
		private ArrayList<Task> tasksAvailable; // T�che ou l'on peut s'inscrire pour y prendre part
		private ArrayList<Task> tasksBeingDone; // T�che en cours de r�alisation
		private ArrayList<Task> tasksDone; // T�che en attente de paiement.
		private ArrayList<Service> services;
		private ArrayList<Reduction> reductions;
		private final int password;
		private final Reduction defaultReduction;
		private ArrayList<Task> taskDoneHistory; // T�che finies.
	
	public Network (Admin admin, String name ) {
		this.password = 100000 + (int)(Math.random() * ((999999 - 100000) + 1));
		this.name = name;
		this.member = new ArrayList<Member>();
		this.addMember(admin, this.password); // Un admin est �galement un membre.
		this.admin = admin;
		this.tasksAvailable = new ArrayList<Task>();
		this.tasksBeingDone = new ArrayList<Task>();
		this.tasksDone = new ArrayList<Task>();
		this.pendingMembers = new ArrayList<Member>();
		this.services = new ArrayList<Service>();
		this.defaultReduction = new Reduction("default", 1.0); // Par d�faut un membre n'a pas de r�duction
		this.reductions = new ArrayList<Reduction>();
		this.taskDoneHistory = new ArrayList<Task>();
		this.allTasks = new ArrayList<Task>();
		reductions.add(this.defaultReduction);
		// cr�� un mot de passe al�atoire, puis le communique � l'admin.
		admin.setNetworkPassword(this.password);
		
		
 }
	
	public void setTaskDone(Task t) {
		this.tasksDone.add(t);
		this.tasksBeingDone.remove(t);
		t.setAdminValidate();
	}
	
	public void setTaskBeingDone(Task t) {
		this.tasksBeingDone.add(t);
		this.tasksAvailable.remove(t);
	}
	
	public String getNetworkName() {
		return this.name;
	}
	
	public boolean equals(Network n) {
		return this.name.equals(n.getNetworkName());
	}
	
	public boolean isTaskAvailable(Task t) {
		
		for(Task t2 : this.tasksAvailable) {
			if (t2.getName().equals(t.getName())) return true;
		}
		return false;
	}
	
	public void deleteTaskAvailable(Task t) {
		this.tasksAvailable.remove(t);
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
	
	public boolean isAdmin(Member m) {
		return this.admin.getName().equals(m.getName());
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
		this.allTasks.add(t);
	}
	
	public ArrayList<Task> getTasks() {
		return this.tasksAvailable;
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
	
	/***
	 * Ajoute une classe sociale s au r�seau.
	 * @param s
	 * @param networkPassword
	 */
	public void addReduction(Reduction s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) this.reductions.add(s);
	}
	
	/**
	 * Supprimer une classe sociale d'un r�seau.
	 * @param s
	 * @param networkPassword
	 */
	public void removeReduction(Reduction s, int networkPassword) {
		// TODO Auto-generated method stub
		if(this.password == networkPassword) {
			for (Member m : this.member) {
				if (m.getReductionValue() == s.getReductionValue()) m.setReduction(this.defaultReduction);
			}
			this.reductions.remove(s);
		}
		
	}

	/**
	 * Retourne les t�ches en attente de validation.
	 * @return
	 */
	public ArrayList<Task> getPendingTasks() {
		// TODO Auto-generated method stub
		return this.tasksBeingDone;
	}

	public boolean isPendingTask(Task t) {
		
		// TODO Auto-generated method stub
		for (Task t2 : this.tasksBeingDone) {
			if (t2.getName().equals(t.getName())) return true;
		}
		return false;
	}
	
	public void removePendingTask(Task t) {
		this.tasksBeingDone.remove(t);
	}

	public void addTaskHistory(Task task) {
		// TODO Auto-generated method stub
		this.taskDoneHistory.add(task);
	}

	public ArrayList<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return this.allTasks;
	}
}

