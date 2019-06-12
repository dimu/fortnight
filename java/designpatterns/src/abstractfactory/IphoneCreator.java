package abstractfactory;

public class IphoneCreator extends AbstractCreator {

	@Override
	void sayhello() {
		new IphoneFactory().sayhello();
	}

}
