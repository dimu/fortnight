package basic;

public class OrSequence {
	public static void main(String[] args) {
		if (xx() || yy()) {
			
		}
		System.out.println("------------------");
		if (yy() || xx()) {
			
		}
	}
	
	public static boolean xx() {
		System.out.println("xx");
		return true;
	}
	
	public static boolean yy() {
		System.out.println("yy");
		return false;
	}
}
