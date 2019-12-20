package src;

	public class Reduction {
			private String nameReduction ;
			private double reduction ;
	
	public Reduction ( String nameReduction , double reduction) {
		
			this.nameReduction = nameReduction ; 
			this.reduction = reduction ;
}
	public double getReductionValue() {
		return this.reduction;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.nameReduction;
	}
}
