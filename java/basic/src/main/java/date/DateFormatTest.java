package date;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateFormatTest {

	public static void main(String[] args) {
		DateFormat df1 = DateFormat.getDateInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		DateFormat df2 = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
		DateFormat df4 = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
//		SimpleDateFormat da = new SimpleDateFormat('yyyy-MM-dd',);
		try {
			Date date1 = df1.parse("2014-12-08");
//			Date date2 = df.parse("12-08-2014");
			Date date3 = df.parse("12/08/2014");
			Date date4 = df2.parse("July 12, 2007");
			Date date5 = df4.parse("2014��12��25��");
			Date date = df.parse("2014-12-08");
			System.out.printf(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
