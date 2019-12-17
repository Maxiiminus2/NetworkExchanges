package src;

public class MemberException extends Exception {
	private String s;
	
	public MemberException(String s) {
		this.s = s;
	}
}
