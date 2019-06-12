package basic;

import java.util.Calendar;

public class ObjectFormatter {
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		String s = String.format("Now is: %1$tm %1$te,%1$tY", c);
		System.out.println(s);
	}
}
