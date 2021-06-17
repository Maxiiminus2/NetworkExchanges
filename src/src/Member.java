package src;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	 * D�termine si un membre � une t�che t � faire.
	 * @param t
	 * @return vrai si le membre doit faire la t�che, faux sinon.
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
	 * Permet de d�poser de l'argent dans le wallet
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
	 * Permet de payer les contributeurs d'une t�che r�alis�e pour nous.
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
			t.setTaskDone(this);
		} else {
			throw new MemberException("Vous n'�tes pas autoris� � valider cette t�che !");
		}
	}
	
	/**
	 * Envoie un montant "amount" de tokens � un membre "m".
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
	 * V�rifie si le membre est qualifi� pour �ffectuer une t�che.
	 * @param s
	 * @return vrai si qualifi�, faux sinon.
	 */
	public boolean canDo(Service s) {
		boolean found = false;
		for (Service s2 : services) {
			found = found || s2.equals(s);
		}
		return found;
	}
	
	/**
	 * Ajoute une t�che que le membre a � faire.
	 * @param t
	 */
	public void addTaskToDo(Task t) {
		this.tasksToDo.add(t);
	}
	
	/**
	 * Ajoute une t�che dont le membre est b�n�ficiaire.
	 * @param t
	 */
	public void addTaskBeneficiary(Task t) {
		this.tasksBeneficiary.add(t);
	}
		
	/**
	 * Cr�� une nouvelle t�che dont le membre sera le b�neficiaire.
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
	 * Ajoute un service de comp�tence du membre.
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

	public int getBeneficiaryTaskNb(Network selectedNetwork) {
		// TODO Auto-generated method stub
		int nb = 0;
		for (Task t : this.getTasksBeneficiary()) {
			if(t.getNetwork().equals(selectedNetwork)) nb++;
		}
		return nb;
	}

	public ArrayList<Task> getBeneficiaryTasksByNetwork(Network selectedNetwork) {
		// TODO Auto-generated method stub
		ArrayList<Task> result = new ArrayList<Task>();
		
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


	public ArrayList<Task> getTasksSubscribed() {
		// TODO Auto-generated method stub
		return this.tasksSubscribed;
	}

	public ArrayList<Task> getTasksToDo() {
		// TODO Auto-generated method stub
		return this.tasksToDo;
	}

	public void setHoursSpent(int nb, Task t) {
		// TODO Auto-generated method stub
		if(this.getName().equals(t.getBeneficiary().getName())){
			t.setHoursSpent(nb);
		}
	}

	public boolean checkPassword(String password2) {
		// TODO Auto-generated method stub
		return this.password.equals(password2);
	}

	public void setReduction(Reduction r) {
		// TODO Auto-generated method stub
		this.reduction = r;
	}

	public boolean hasTasks() {
		// TODO Auto-generated method stub
		return this.tasksBeneficiary.size()+this.tasksSubscribed.size()+this.tasksBeneficiary.size() > 0;
	}
	

	
}