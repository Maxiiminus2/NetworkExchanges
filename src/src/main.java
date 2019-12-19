package src;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin lea = new Admin("Léa", 0, "abc");
		Network network = lea.createNetwork("Network POO");
		Reduction classeNormale = new Reduction("Classe normale", 1);
		Reduction classeMoyenne = new Reduction("Classe moyenne", 0.5);
		Member Pierre = new Member("Pierre", 150, classeMoyenne, "abc");
		Service jardinage =  new Service("Jardinage", 10);
		Service bricolage = new Service("Bricolage", 15);
		Member Jean = new Member("Jean", 0, classeMoyenne, "abc");
		Jean.addService(jardinage);
		Pierre.addService(bricolage);
		lea.addService(bricolage);
		lea.addService(jardinage);
		
	
		Pierre.addTask(network, "Mes couilles sur ton front", 2, false, jardinage);
		lea.addTask(network, "Tâche test", 4, true, bricolage);
		try {
			network.getTasks().get(0).addContributor(Jean);
			network.getTasks().get(0).addContributor(lea);
			network.getTasks().get(1).addContributor(Pierre);

		} catch (TaskException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		network.getTasks().get(0).validateTask();
		network.getTasks().get(1).validateTask();
		
		System.out.println(Pierre.getWallet());
		System.out.println(lea.getWallet());
		System.out.println(Jean.getWallet());
		
		try {
			Pierre.setTaskDone(Pierre.getTasksBeneficiary().get(0), 4);
		} catch (MemberException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Pierre.payContributors(network.getTasks().get(0));
		} catch (MemberException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		
		System.out.println(Pierre.getWallet());
		System.out.println(lea.getWallet());
		System.out.println(Jean.getWallet());
		
		System.out.println(network.toStringAvailableTasks());
		
	}

}
