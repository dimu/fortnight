package abstractfactory;

public class IphoneFactory extends AbstractProduct {

	@Override
	public void sayhello() {
		System.out.println("I'm iphone!");
	}
}
