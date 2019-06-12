package abstractfactory;

public class FactoryTest {

	public static void main(String[] args) {
		AbstractCreator xx1 = new HuaweiCreator();
		xx1.sayhello();
		xx1 = new IphoneCreator();
		xx1.sayhello();
	}

}
