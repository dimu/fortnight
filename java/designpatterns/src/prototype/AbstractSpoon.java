package prototype;

public abstract class AbstractSpoon implements Cloneable {
	String spoonName;

	public void setSpoonName(String spoonName) {
		this.spoonName = spoonName;
	}
	
	public String getSpoonName() {return this.spoonName;}
	
	/**
	 * 实现clone操作
	 */
	public Object clone(){
		Object object = null;
		try {
			object = super.clone();
		} catch (CloneNotSupportedException exception) {
			System.err.println("AbstractSpoon is not Cloneable");
		}
		return object;
	}
	
	public static void main(String[] args) {
		AbstractSpoon soup = new SoupSpoon();
		Object soupClone = soup.clone();
		if (soupClone instanceof SoupSpoon) {
			System.out.println("clone object is SoupSpoon instance"); //运行结果ok
			System.out.println(((SoupSpoon) soupClone).getSpoonName());
		}
	}
}

class SoupSpoon extends AbstractSpoon
{ 
	public SoupSpoon()
	{
		setSpoonName("Soup Spoon");
	}
}
class SaladSpoon extends AbstractSpoon{ 
	public SaladSpoon(){
		setSpoonName("Salad Spoon");
	}
}

