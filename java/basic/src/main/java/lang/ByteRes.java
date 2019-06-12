package lang;

import java.util.stream.Stream;

/**
 * byte类的相关研究,byte值范围为-128到127,在实现细节上采用缓存方式，
 * java中通常将byte作为数据转换的中间桥梁，需要
 * 
 * @author dwx
 *
 */
public class ByteRes {
	
	//define a byte type constant
	public static final byte b = 'a';
	
	public static final String str = "abc";
	
	public static void main(String[] args) {
		
		System.out.println((int)b);
		
		System.out.println(Byte.valueOf(Byte.toString(b)).byteValue());
		
		System.out.println(Byte.valueOf(b));
		
		System.out.println(Byte.parseByte("97"));
		
		byte[] strBytes = str.getBytes();
		for (int i = 0; i < strBytes.length; i++) {
			System.out.println(strBytes[i]);
		}
	}

}
