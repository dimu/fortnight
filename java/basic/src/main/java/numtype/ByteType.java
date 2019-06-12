package numtype;

import org.junit.Test;

public class ByteType {

	@Test
	public void test() {
		Byte a = (byte)'a';
		System.out.println(a.byteValue());
		a = Byte.valueOf("97");
		System.out.println(a.byteValue());
		System.out.println(a.toString());
		System.out.println(a >> 6);
		System.out.println(Integer.valueOf("11111111", 2));
	}
}
