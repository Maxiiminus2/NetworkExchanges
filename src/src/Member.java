package src;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member {
	String username ; 
	int wallet ;
	private ArrayList<Service> services ;
	private ArrayList<Task> tasksSubscribed;
	private ArrayList<Task> tasksToDo;
	private ArrayList<Task> tasksBeneficiary;
	private Reduction reduction;
	private String password;

	public Member (String username , int wallet, Reduction reduction, String password ) {
		this.services =  new ArrayList<Service>();
		this.tasksToDo = new ArrayList<Task>();
		this.tasksBeneficiary = new ArrayList<Task>();
		this.tasksSubscribed = new ArrayList<Task>();
		this.username = username ;
		this.wallet = wallet;
		this.reduction = reduction;
		this.password = password;
	}
	
	public Member (String username , int wallet, String password ) {
		this.services =  new ArrayList<Service>();
		this.tasksToDo = new ArrayList<Task>();
		this.tasksBeneficiary = new ArrayList<Task>();
		this.tasksSubscribed = new ArrayList<Task>();
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
	 * Détermine si un membre à une tâche t à faire.
	 * @param t
	 * @return vrai si le membre doit faire la tâche, faux sinon.
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
	 * Permet de déposer de l'argent dans le wallet
	 * @param amount
	 */
	public void depositMoney(int amount) {
		this.wallet += amount;
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
	 * Permet de payer les contributeurs d'une tâche réalisée pour nous.
	 * @param t
	 * @throws MemberException
	 */
	public void payContributors(Task t) throws MemberException {
		if (t.getBeneficiary().equals(this) && this.wallet >= t.getBeneficiaryPrice()) {
			t.payContributors(this);
		}
	}
	
	public ArrayList<Task> getTasksBeneficiary() {
		return this.tasksBeneficiary;
	}
	
	public void setTaskDone(Task t, int hoursSpent) throws MemberException {
		if(t.getBeneficiary().equals(this)) {
			t.setTaskDone(this, hoursSpent);
		} else {
			throw new MemberException("Vous n'êtes pas autorisé à valider cette tâche !");
		}
	}
	
	/**
	 * Envoie un montant "amount" de tokens à un membre "m".
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
	 * Vérifie si le membre est qualifié pour éffectuer une tâche.
	 * @param s
	 * @return vrai si qualifié, faux sinon.
	 */
	public boolean canDo(Service s) {
		boolean found = false;
		for (Service s2 : services) {
			found = found || s2.equals(s);
		}
		return found;
	}
	
	/**
	 * Ajoute une tâche que le membre a à faire.
	 * @param t
	 */
	public void addTaskToDo(Task t) {
		this.tasksToDo.add(t);
	}
	
	/**
	 * Ajoute une tâche dont le membre est bénéficiaire.
	 * @param t
	 */
	public void addTaskBeneficiary(Task t) {
		this.tasksBeneficiary.add(t);
	}
		
	/**
	 * Créé une nouvelle tâche dont le membre sera le béneficiaire.
	 * @param n
	 * @param name
	 * @param contributorsRequiredNb
	 * @param isVolontary
	 * @param service
	 */
	public void addTask(Network n, String name, int contributorsRequiredNb, boolean isVolontary, Service service, int estimatedHours) {
		Task newTask = new Task(name, contributorsRequiredNb, this, isVolontary, service, estimatedHours);
		n.addTask(newTask);
		this.addTaskBeneficiary(newTask);
	}

	/**
	 * Ajoute un service de compétence du membre.
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
		// TODO Auto-generated method stub
		return this.password;
	}

	public ArrayList<Service> getServices() {
		// TODO Auto-generated method stub
		return this.services;
	}

	public void removeService(Service service) {
		// TODO Auto-generated method stub
		this.services.remove(service);
		
	}

	public void setPassword(String newPassword) {
		// TODO Auto-generated method stub
		this.password = newPassword;
		
	}

	public void setUsername(String newUsername) {
		// TODO Auto-generated method stub
		this.username = newUsername;
	}
	

	
}