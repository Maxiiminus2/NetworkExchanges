package src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member {
	String name ; 
	int wallet ;
	private ArrayList<Service> services ;
	private ArrayList<Task> tasksToDo;
	private ArrayList<Task> tasksBeneficiary;
	private Reduction reduction;

	public Member (String name , int wallet, Reduction reduction ) {
		this.services =  new ArrayList<Service>();
		this.tasksToDo = new ArrayList<Task>();
		this.tasksBeneficiary = new ArrayList<Task>();
		this.name = name ;
		this.wallet = wallet;
		this.reduction = reduction;
	}
	
	public Reduction getReduction() {
		return this.reduction;
	}
	
	/**
	 * D�termine si un membre � une t�che t � faire.
	 * @param t
	 * @return vrai si le membre doit faire la t�che, faux sinon.
	 */
	public boolean hasTask(Task t) {
		boolean hasTask = false;
		for (Task task : this.tasksToDo) {
			hasTask = hasTask || t.equals(task);
		}
		
		return hasTask;
	}
	
	/**
	 * Permet de d�poser de l'argent dans le wallet
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
	 * Permet de payer les contributeurs d'une t�che r�alis�e pour nous.
	 * @param t
	 * @throws MemberException
	 */
	public void payContributors(Task t) throws MemberException {
		if (t.getBeneficiary().equals(this) && this.wallet >= t.getFullPrice()) {
			t.payContributors(this);
		}
	}
	
	public void setTaskDone(Task t) throws MemberException {
		if(t.getBeneficiary().equals(this)) {
			t.setTaskDone();
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
	public void addTask(Network n, String name, int contributorsRequiredNb, boolean isVolontary, Service service) {
		Task newTask = new Task(name, contributorsRequiredNb, this, isVolontary, service);
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
		return this.name;
	}
	

	
}