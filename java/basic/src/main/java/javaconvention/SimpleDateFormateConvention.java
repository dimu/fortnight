package javaconvention;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SimpleDateFormateConvention {

	private static ThreadLocal<SimpleDateFormat> SDFLocal = new ThreadLocal<SimpleDateFormat>(){
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	public void format() throws ParseException {
		SimpleDateFormat sdf = SDFLocal.get();
		System.out.println(sdf.format(Calendar.getInstance().getTime()));
	}
	
	public void parser() throws ParseException {
		SimpleDateFormat sdf = SDFLocal.get();
		System.out.println(sdf.parse("2018--08-08 23:12:14"));
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormateConvention sdfc = new SimpleDateFormateConvention();
		sdfc.format();
		sdfc.parser();
	}

}
