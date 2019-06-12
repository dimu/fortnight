/**
 */
package basic;

public class MutableObjectCopy implements Cloneable{
	private String name;
	private int age;
	
	public MutableObjectCopy() {
	}
	
	public MutableObjectCopy(MutableObjectCopy para) {
		this.setAge(para.getAge());
		this.setName(para.getName());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static MutableObjectCopy getMutableObjectCopy(MutableObjectCopy objectParam) throws CloneNotSupportedException {
//		return (MutableObjectCopy) objectParam.clone();
		return objectParam.copy(objectParam);
	}
	
	public MutableObjectCopy copy(MutableObjectCopy para) {
		
		MutableObjectCopy ret = new MutableObjectCopy();
		ret.setAge(para.getAge());
		ret.setName(para.getName());
		
		return ret;
	}
	
}
