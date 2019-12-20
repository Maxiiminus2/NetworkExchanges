package src;


public class Admin extends Member {
	
	private int networkPassword;
	
	public Admin(String name , int wallet, String password) {
		super(name, wallet, null, password);
	}
	
	
	public Network createNetwork (String n) {
		return new Network(this, n);
	}

	 public void addMember(Network n , Member m) {
		 n.addMember(m, this.networkPassword);
	}
	 
	 public void removeMember(Network n, Member m) {
		 n.removeMember(m, this.networkPassword);
	 }
	 
	 public void setNetworkPassword(int password) {
		 this.networkPassword = password;
	 }
	 
	 public void addService(Network n, Service s) {
		 n.addService(s, this.networkPassword);
	 }


	 public void removeService(Network n, Service s) {
		// TODO Auto-generated method stub
		n.removeService(s, this.networkPassword);
	 }
	 
	 public void addReduction(Network n, Reduction s) {
		 n.addReduction(s, this.networkPassword);
	 }


	 public void removeReduction(Network n, Reduction s) {
		// TODO Auto-generated method stub
		n.removeReduction(s, this.networkPassword);
	 }
	 
	 /* public boolean validTask(Task t) {
	  * 	if(t.isDone()) return true;
	  * }
	  * 
	  */
	 
}
