package basic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SimpleDateFormatSafe{
	private SimpleDateFormat sdf;
	
//	private SimpleDateFormatSafe(){
//		
//	};
	
	public void m1() {
		System.out.println(sdf.format(Calendar.getInstance().getTime()));
	}
	
	public void m2(){
		System.out.println(sdf.format(Calendar.getInstance().getTime()));
	}
	
	public void m2(String a){
	
	}

}
