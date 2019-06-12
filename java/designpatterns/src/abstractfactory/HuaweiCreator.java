package abstractfactory;

public class HuaweiCreator extends AbstractCreator {

	@Override
	void sayhello() {
		
		new HuaweiFactory().sayhello();
	}

}
