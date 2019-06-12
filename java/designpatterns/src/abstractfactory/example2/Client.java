package abstractfactory.example2;

public class Client {
	
	public Client() {
		new Factory1().create();
	}
	
	public static void main(String[] args) {
		Client a = new Client();
		System.out.println("client xx");
	}
}
