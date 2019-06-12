package abstractfactory.example2;

public class Factory1 extends AbstractFactory {

	public Product create() {
		
		return new Product11();
	}

}
