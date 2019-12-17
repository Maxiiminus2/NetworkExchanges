package src;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin lea = new Admin("Léa", 0);
		Network network = lea.createNetwork("Network POO");
		Member Pierre = new Member("Pierre", 150, null);
		Service jardinage =  new Service("Jardinage", 10);
		Service bricolage = new Service("Bricolage", 15);
		Member Jean = new Member("Jean", 0, null);
		Jean.addService(jardinage);
		Pierre.addService(bricolage);
		lea.addService(bricolage);
		
	
		Pierre.addTask(network, "Mes couilles sur ton front", 3, false, jardinage);
		lea.addTask(network, "Tâche test", 4, true, bricolage);
		try {
			network.getTasks().get(0).addContributor(Jean);
			network.getTasks().get(1).addContributor(Pierre);
		} catch (TaskException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		System.out.println(network.toStringAvailableTasks());
		
	}

}
