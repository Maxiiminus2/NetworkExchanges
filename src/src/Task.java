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
			if (m.canDo(this.service) && !m.equals(this.beneficiary) && !m.hasTask(this)) {
				this.contributors[contributorsNb] = m;
				this.contributorsNb++;
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
		for (Member m : contributors) {
			m.addTaskToDo(this);
		}
	}
	
	public boolean isTaskDone() {
		return this.isDone;
	}	
	
	public void setTaskDone() {
		this.isDone = true;
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
	
	/**
	 * Permet de payer les contributeurs lorsqu'ils ont effectu� une t�che.
	 * @param member
	 * @throws MemberException
	 */
	public void payContributors(Member member) throws MemberException {
		if (member.equals(this.beneficiary)) {
			int amountPerContributor = (int) ( this.service.hourlyCost * this.hoursSpent);
			for (Member m : contributors) {
				member.sendMoney(amountPerContributor, member);
			}
		}
	}
}
