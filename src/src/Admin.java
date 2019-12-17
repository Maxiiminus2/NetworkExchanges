package src;


public class Admin extends Member {
	
	private int networkPassword;
	
	public Admin(String name , int wallet) {
		super (name, wallet, null);
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
	 
	 /* public boolean validTask(Task t) {
	  * 	if(t.isDone()) return true;
	  * }
	  * 
	  */
	 
}
