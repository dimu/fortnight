package basic;

public class MutableObjectCopyTest {
	public static void main(String[] args) throws CloneNotSupportedException{
		MutableObjectCopy or = new MutableObjectCopy();
		or.setAge(11);
		or.setName("xiaojuancai");
		MutableObjectCopy copy1 = MutableObjectCopy.getMutableObjectCopy(or);

	}
}
