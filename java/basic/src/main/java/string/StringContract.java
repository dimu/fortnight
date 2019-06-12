package string;

import java.util.Calendar;

import org.junit.Test;

public class StringContract {

	public static void main(String[] args) {
		String s = "";
		StringBuffer s1 = new StringBuffer();
		StringBuilder s2 = new StringBuilder();
		long counter = 65536;
		
		long c1 = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i<counter; i++) {
			s += "abc"+"abc";
		}
		long c2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(c2-c1);
		
		for (int i = 0; i<counter; i++) {
			s1.append("abc"+"abc"+"abc");
		}
		long c3 = Calendar.getInstance().getTimeInMillis();
		System.out.println(c3-c2);
		
		for (int i = 0; i<counter; i++) {
			s2.append("abc").append("abc").append("abc");
		}
		long c4 = Calendar.getInstance().getTimeInMillis();
		System.out.println(c4-c3);
	}
	
	@Test
	public void getByte() {
	    StringBuffer sb = new StringBuffer("ACT,,01");
	    byte[] bt = sb.toString().getBytes();
	    for(int i = 0; i< bt.length; i++) {
	        System.out.println(bt[i]);
	    }
	}
	
	@Test
	public void testAdrress() {
	    String str = new String("abc");
	    System.out.println(str);
	}

}
