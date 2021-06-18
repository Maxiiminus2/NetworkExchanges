package src;
import java.util.ArrayList;
import java.util.List;

public class Member {
	String username; 
	int wallet;
	private ArrayList<Service> services;
	private ArrayList<Task> tasksSubscribed;
	private ArrayList<Task> tasksToDo;
	private ArrayList<Task> tasksBeneficiary;

	private Reduction reduction;
	private String password;

	public Member (String username , int wallet, Reduction reduction, String password ) {
		this.services =  new ArrayList<>();
		this.tasksToDo = new ArrayList<>();
		this.tasksBeneficiary = new ArrayList<>();
		this.tasksSubscribed = new ArrayList<>();
	
		this.username = username ;
		this.wallet = wallet;
		this.reduction = reduction;
		this.password = password;
	}
	
	public Member (String username , int wallet, String password ) {
		this.services =  new ArrayList<>();
		this.tasksToDo = new ArrayList<>();
		this.tasksBeneficiary = new ArrayList<>();
		this.tasksSubscribed = new ArrayList<>();
		this.username = username ;
		this.wallet = wallet;
		this.reduction = new Reduction("default", 1.0);
		this.password = password;
	}
	
	public double getReductionValue() {
		return this.reduction.getReductionValue();
	}
	
	public boolean equals(Member m) {
		return this.getName().equals(m.getName());
	}
	
	/**
	 * Determine si un membre a une tache t a faire.
	 * @param t
	 * @return vrai si le membre doit faire la tache, faux sinon.
	 */
	public boolean hasTaskToDo(Task t) {
		boolean hasTask = false;
		for (Task task : this.tasksToDo) {
			hasTask = hasTask || t.equals(task);
		}
		
		return hasTask;
	}
	
	public boolean isSubscribedToTask(Task t) {
		boolean subscribed = false;
		for (Task task : this.tasksSubscribed) {
			subscribed = subscribed || t.equals(task);
		}
		return subscribed;
	}
	
	public void addTaskSubscribed(Task t) {
		this.tasksSubscribed.add(t);
	}
	
	/**
	 * Permet de deposer de l'argent dans le wallet
	 * @param amount
	 */
	public void depositMoney(int amount) {
		this.wallet += amount;
	}
	
	public void removeBeneficiaryTask(Task t) {
		this.tasksBeneficiary.remove(t);
		for(int i = 0 ; i < t.getContributorsNb() ; i++){
			Member m = t.getContributorsArray()[i];
			if(t.getStatus().equals("Being done")) {
				m.removeTaskToDo(t);
			} else if(t.getStatus().equals("Waiting for contributors")) {
				m.removeSubscribedTask(t);
			}
		}
	}
	
	public void removeSubscribedTask(Task t) {
		this.tasksSubscribed.remove(t);
	}
	
	public void removeTaskToDo(Task t) {
		this.tasksToDo.remove(t);
	}
	
	public void validateTask(Task t) {
		t.setTaskToDo(this);
	}
	
	
	/**
	 * Permet de retirer de l'argent du wallet
	 * 
	 * @param amount
	 * @return la nouvelle valeur du solde du compte.
	 * @throws MemberException
	 */
	public int withdrawMoney(int amount) throws MemberException {
		if(this.wallet >= amount) {
			this.wallet -= amount;
			return this.wallet;
		} else {
			throw new MemberException("Pas assez d'argent sur le compte.");
		}
	}
	
	/**
	 * Permet de payer les contributeurs d'une tache realisee pour nous.
	 * @param t
	 * @throws MemberException
	 */
	public void payContributors(Task t) throws MemberException {
		if (t.getBeneficiary().equals(this) && this.wallet >= t.getBeneficiaryPrice()) {
			t.payContributors(this);
		}
	}
	
	public List<Task> getTasksBeneficiary() {
		return this.tasksBeneficiary;
	}
	
	public void setTaskDone(Task t) throws MemberException {
		if(t.getBeneficiary().equals(this)) {
			t.setTaskDone(this);
		} else {
			throw new MemberException("Vous n'etes pas autorise a valider cette tache !");
		}
	}
	
	/**
	 * Envoie un montant "amount" de tokens a un membre "m".
	 * @param amount
	 * @param m
	 * @throws MemberException
	 */
	public void sendMoney(int amount, Member m) throws MemberException {
		if(this.wallet >= amount) {
			m.receiveMoney(amount);
			this.wallet -= amount;
		} else {
			throw new MemberException("Vous n'avez pas assez d'argent sur votre compte !");
		}
	}
	
	public void receiveMoney(int amount) {
		this.wallet += amount;
	}

	/**
	 * Verifie si le membre est qualifie pour effectuer une tache.
	 * @param s
	 * @return vrai si qualifie, faux sinon.
	 */
	public boolean canDo(Service s) {
		boolean found = false;
		for (Service s2 : services) {
			found = found || s2.equals(s);
		}
		return found;
	}
	
	/**
	 * Ajoute une tache que le membre a a faire.
	 * @param t
	 */
	public void addTaskToDo(Task t) {
		this.tasksToDo.add(t);
	}
	
	/**
	 * Ajoute une tache dont le membre est beneficiaire.
	 * @param t
	 */
	public void addTaskBeneficiary(Task t) {
		this.tasksBeneficiary.add(t);
	}
		
	/**
	 * Cree une nouvelle tache dont le membre sera le beneficiaire.
	 * @param n
	 * @param name
	 * @param contributorsRequiredNb
	 * @param isVolontary
	 * @param service
	 */
	public void addTask(Network n, String name, int contributorsRequiredNb, boolean isVolontary, Service service, int estimatedHours) {
		Task newTask = new Task(name, contributorsRequiredNb, this, isVolontary, service, estimatedHours, n);
		n.addTask(newTask);
		this.addTaskBeneficiary(newTask);
	}

	/**
	 * Ajoute un service de competence du membre.
	 * @param s
	 */
	public void addService(Service s) {
		this.services.add(s);
		
	}
	
	public String getName() {
		return this.username;
	}

	public int getWallet() {
		return this.wallet;
	}

	public String getPassword() {
		return this.password;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void removeService(Service service) {
		this.services.remove(service);
		
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
		
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public int getBeneficiaryTaskNb(Network selectedNetwork) {
		int nb = 0;
		for (Task t : this.getTasksBeneficiary()) {
			if(t.getNetwork().equals(selectedNetwork)) nb++;
		}
		return nb;
	}

	public List<Task> getBeneficiaryTasksByNetwork(Network selectedNetwork) {
		ArrayList<Task> result = new ArrayList<>();
		
		for (Task t : this.tasksBeneficiary) {
			if (t.getNetwork().equals(selectedNetwork)) result.add(t);
		}
		
		return result;
	}

	public Task getBeneficiaryTask(String taskName) {
		
		for (Task t : this.getTasksBeneficiary()) {
			if (t.getName().equals(taskName)) return t;
		}
		return null;
	}


	public List<Task> getTasksSubscribed() {
		return this.tasksSubscribed;
	}

	public List<Task> getTasksToDo() {
		return this.tasksToDo;
	}

	public void setHoursSpent(int nb, Task t) {
		if(this.getName().equals(t.getBeneficiary().getName())){
			t.setHoursSpent(nb);
		}
	}

	public boolean checkPassword(String password2) {
		return this.password.equals(password2);
	}

	public void setReduction(Reduction r) {
		this.reduction = r;
	}

	public boolean hasTasks() {
		return this.tasksBeneficiary.size()+this.tasksSubscribed.size()+this.tasksBeneficiary.size() > 0;
	}
	

	
}