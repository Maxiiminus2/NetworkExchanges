package src;

import java.util.Map;

public class Task {
	
	private String name;
	private int contributorsRequiredNb;
	private int contributorsNb;
	private Member[] contributors;
	private Member beneficiary;
	private boolean isVolontary;
	private boolean isDone;
	private int hoursSpent;
	private Service service;
	
	// private Map<Member, Boolean> done;
	
	public Task(String name, int contributorsRequiredNb, Member beneficiary, boolean isVolontary, Service service) {
		this.contributorsRequiredNb = contributorsRequiredNb;
		this.name = name;
		this.contributorsNb = 0;
		this.beneficiary = beneficiary;
		this.isVolontary = isVolontary;
		this.contributors = new Member[contributorsRequiredNb];
		this.isDone = false;
		this.service = service;
		this.hoursSpent = 0;
	}
	
	public boolean equals(Task t) {
		return this.name.equals(t.name);
	}
	
	public String toString() {
		String result = this.service.name + " : " + this.name + "\n" + this.contributorsNb + "/" + this.contributorsRequiredNb + " contributeurs.";
		return result;
	}
	
	/**
	 * Ajoute un contributeur � une t�che.
	 * @param m
	 * @throws TaskException
	 */
	public void addContributor(Member m) throws TaskException{
		if (this.contributorsNb < this.contributorsRequiredNb) {
			if (m.canDo(this.service) && !m.equals(this.beneficiary) && !m.isSubscribedToTask(this)) {
				this.contributors[contributorsNb] = m;
				this.contributorsNb++;
				m.addTaskSubscribed(this);
			} else {
				throw new TaskException("Ce membre n'est pas qualifi� pour r�aliser cette t�che !");
			}
		} else {
			throw new TaskException("Le nombre de contributeurs requis est d�j� atteint !");
		}
	}
	
	/**
	 * Retourne le nombre de contributeurs requis pour effectuer la t�che.
	 * @return
	 */
	public int getContributorsRequiredNb() {
		return this.contributorsRequiredNb;
	}
	
	/** 
	 * Retourne le nombre de contributeurs inscrits pour effectuer la t�che.
	 * @return
	 */
	public int getContributorsNb() {
		return this.contributorsNb;
	}
	
	/**
	 * Permet de valider une t�che et de l'afficher que t�che � faire aux contributeurs.
	 */
	public void validateTask() {
		if (this.enoughMembers()) {
			for (Member m : contributors) {
				m.addTaskToDo(this);
			}
		}
	}
	
	public boolean isTaskDone() {
		return this.isDone;
	}	
	
	public void setTaskDone(Member m, int hoursSpent) {
		if(this.getBeneficiary().equals(m)) {
			this.isDone = true;
			this.hoursSpent = hoursSpent;
		}
		
		
	}
	
	public Member getBeneficiary() {
		return this.beneficiary;
	}
	
	public Member[] getContributorsArray(){
		return this.contributors;
	}
	
	/**
	 * D�termine s'il y a assez de membres pour effectuer une t�che.
	 * @return
	 */
	public boolean enoughMembers() {
		return this.contributorsRequiredNb == this.contributorsNb;
	}

	/**
	 * Retourne le prix total que la t�che co�te sans prendre en compte la r�duction du b�n�ficiaire.
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
	
	public boolean isVolontary() {
		return this.isVolontary;
	}
	
	/**
	 * Permet de payer les contributeurs lorsqu'ils ont effectu� une t�che.
	 * @param member
	 * @throws MemberException
	 */
	public void payContributors(Member benef) throws MemberException {
		if (benef.equals(this.beneficiary)) {
			int amountPerContributor = (int) ( this.service.hourlyCost * this.hoursSpent);
			int amountPerContributorLeft = (int) (amountPerContributor - amountPerContributor * benef.getReductionValue());
			for (Member c : contributors) {
				benef.sendMoney( (int)(amountPerContributor * benef.getReductionValue()), c);
				if (benef.getReductionValue() != 1) c.receiveMoney(amountPerContributorLeft);
			}
		}
	}
}
