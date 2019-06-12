package date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarAndDate {
	enum A{};
	
	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//		int year = now.getYear();
		int year = calendar.get(Calendar.YEAR);
//		now.setYear(year+20);
		calendar.set(Calendar.YEAR, year+20);
		String rangeMax = fmt.format(calendar.getTime())+" 00:00";
		System.out.println(rangeMax);
//		now.setYear(year-20);
		calendar.set(Calendar.YEAR, year-20);
		String rangeMin= fmt.format(calendar.getTime())+" 00:00";
		System.out.print(rangeMin);
	}
}
