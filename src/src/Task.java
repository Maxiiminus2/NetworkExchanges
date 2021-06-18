package src;

public class Task {
	
	private String name;
	private int contributorsRequiredNb;
	private int contributorsNb;
	private Member[] contributors;
	private Member beneficiary;
	private boolean isVolontary;
	private boolean isDone;
	private int hoursSpent;
	private int estimatedHoursNeeded;
	private Service service;
	private Network network;
	private String status;
		
	public Task(String name,  int contributorsRequiredNb, Member beneficiary, boolean isVolontary, Service service, int estimatedHours, Network network) {
		this.contributorsRequiredNb = contributorsRequiredNb;
		this.name = name;
		this.contributorsNb = 0;
		this.beneficiary = beneficiary;
		this.isVolontary = isVolontary;
		this.contributors = new Member[contributorsRequiredNb];
		this.isDone = false;
		this.service = service;
		this.hoursSpent = 0;
		this.estimatedHoursNeeded = estimatedHours;
		this.network = network;
		this.status = "Waiting for contributors";
	}
	
	public boolean equals(Task t) {
		return this.name.equals(t.name);
	}
	
	/**
	 * Ajoute un contributeur a une tache.
	 * @param m
	 * @throws TaskException
	 */
	public void addContributor(Member m) throws TaskException{
		if (this.contributorsNb < this.contributorsRequiredNb) {
			if (m.canDo(this.service)) {
				if (!m.equals(this.beneficiary)) {
					if (!m.isSubscribedToTask(this)) {
						this.contributors[contributorsNb] = m;
						this.contributorsNb++;
						m.addTaskSubscribed(this);
					} else {
						throw new TaskException("Vous participez deja a la tache !");
					}
				} else {
					throw new TaskException("Vous etes le beneficiaire de cette tache !");
				}
				
			} else {
				throw new TaskException("Ce membre n'est pas qualifie pour realiser cette tache !");
			}
		} else {
			throw new TaskException("Le nombre de contributeurs requis est deja atteint !");
		}
	}
	
	/**
	 * Retourne le nombre de contributeurs requis pour effectuer la tache.
	 * @return
	 */
	public int getContributorsRequiredNb() {
		return this.contributorsRequiredNb;
	}
	
	/** 
	 * Retourne le nombre de contributeurs inscrits pour effectuer la tache.
	 * @return
	 */
	public int getContributorsNb() {
		return this.contributorsNb;
	}
	
	/**
	 * Permet de valider une tache et de l'afficher que tache a faire aux contributeurs.
	 */
	public void setTaskToDo(Member b) {
		if (this.enoughMembers() && this.getBeneficiary().getName().equals(b.getName())) {
			this.getNetwork().setTaskBeingDone(this);
			this.status = "Being done";


			for (Member m : contributors) {
				m.addTaskToDo(this);
				m.removeSubscribedTask(this);
			}
		}
	}
	
	public boolean isTaskDone() {
		return this.isDone;
	}	
	
	public void setTaskDone(Member m) {
		if(this.getBeneficiary().equals(m)) {
			this.isDone = true;
			this.status = "Done";
			this.network.addTaskHistory(this);
		}
		
		
	}
	
	public Member getBeneficiary() {
		return this.beneficiary;
	}
	
	public Member[] getContributorsArray(){
		return this.contributors;
	}
	
	/**
	 * Determine s'il y a assez de membres pour effectuer une tache.
	 * @return
	 */
	public boolean enoughMembers() {
		return this.contributorsRequiredNb == this.contributorsNb;
	}

	/**
	 * Retourne le prix total que la tache coute sans prendre en compte la reduction du beneficiaire.
	 * @return
	 */
	public int getFullPrice() {
		return (int) (this.service.hourlyCost * this.hoursSpent * this.contributorsNb);
	}
	
	public int getBeneficiaryPrice() {
		if (this.isVolontary()) return 0;
		else {
			return (int) (this.getFullPrice() * this.getBeneficiary().getReductionValue());
		}
	}
	
	/**
	 * Retourne le prix estime de la tache.
	 * @return
	 */
	public int getEstimatedBeneficiaryPrice() {
		if  (this.isVolontary()) return 0;
		else {
			return (int) (this.getBeneficiary().getReductionValue() * this.getService().getHourlyCost() * this.estimatedHoursNeeded * this.contributorsRequiredNb);
		}
	}
	
	public boolean isVolontary() {
		return this.isVolontary;
	}
	
	/**
	 * Permet de payer les contributeurs lorsqu'ils ont effectue une tache.
	 * @param member
	 * @throws MemberException
	 */
	public void payContributors(Member benef) throws MemberException {
		if (benef.equals(this.beneficiary)) {
			// Montant que doit payer le beneficiaire.
			int amountPerContributor = (int) ( this.service.hourlyCost * this.hoursSpent);
			// Montant manquant que l'entreprise paye.
			int amountPerContributorLeft = (int) (amountPerContributor - amountPerContributor * benef.getReductionValue());
			for (Member c : contributors) {
				benef.sendMoney( (int)(amountPerContributor * benef.getReductionValue()), c);
				if (benef.getReductionValue() != 1) c.receiveMoney(amountPerContributorLeft);
			}
		}
	}

	public String getName() {
		return this.name;
	}

	public Service getService() {
		return this.service;
	}

	public double getEstimatedHours() {
		return this.estimatedHoursNeeded;
	}

	public Network getNetwork() {
		return this.network;
	}

	public String getStatus() {
		return this.status;
	}

	public void removeContributor(Member m) {
		
		int cIndex = this.getContributorIndex(m);
		if (cIndex != -1) {
			
			m.removeSubscribedTask(this);
			
			// On deplace tous les contributeurs de une case vers la gauche depuis celui qu'on supprime
			for (int i = cIndex ; i < this.contributorsNb ; i++) {
				if (i < this.contributorsRequiredNb - 1) this.contributors[i] = this.contributors[i+1];
			}
			// Le dernier apparait deux fois, on le supprime.
			this.contributors[this.contributorsNb-1] = null;
		} else {
			System.out.println("Ne participe pas a la tache");
		}
		
		this.contributorsNb--;
	}
	
	/**
	 * Retourne l'index du membrer m dans la liste des contributeurs.
	 * @param m
	 * @return
	 */
	private int getContributorIndex(Member m) {
		for (int i = 0 ; i < this.contributorsRequiredNb ; i++) {
			Member c = this.contributors[i]; 
			if(c != null && c.getName().equals(m.getName())) return i;
		}
		
		return -1;
	}
	
	
	public void setAdminValidate() {
		this.status = "Waiting for payement";
	}

	public void setHoursSpent(int nb) {
		this.hoursSpent = nb;
	}
}
