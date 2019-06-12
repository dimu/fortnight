package java8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.junit.Test;

/**
 * description: java8新的date以及time机制
 *
 * @version 2017年5月4日 下午3:36:51
 * @see modify content------------author------------date
 */
public class Java8DateAndTime {

	@Test
	public void localDateAndTimeTest() {
		System.out.println("------------start local date time-------------------");
		// 获取当前时间
		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println(currentTime);

		// 获取当前日期
		LocalDate currentDate = currentTime.toLocalDate();
		System.out.println(currentDate);

		// 获取当前时刻
		LocalTime time = currentTime.toLocalTime();
		System.out.println(time);

		// 获取小时
		int hour = currentTime.getHour();
		System.out.println(hour);

		// 获取月份
		int month = currentTime.getMonthValue();
		System.out.println(month);

		// 获取
		System.out.println(currentTime.with(ChronoField.YEAR, 2018));

		// 时刻也支持with操作
		System.out.println(time.withHour(12).withMinute(55));

		// 通过of工厂，构造LocalDate对象
		LocalDate localDate1 = LocalDate.of(2017, 4, 5);
		System.out.println(localDate1);

		// 计算两者相差的时间间隔
		long intervalDay = localDate1.until(LocalDate.now(), ChronoUnit.DAYS);
		System.out.println("interval days:" + intervalDay);

		// 日期的月份加1
		System.out.println("add one month to 2017-04-05:" + localDate1.plusMonths(1));

		// 按照指定格式解析datetime
		LocalDateTime parserDateTime = LocalDateTime.parse("2017-05-05 12:13:55",
				DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
		System.out.println(parserDateTime);

		// 将java.util.Date对象转换为java.time.LocalDate对象
		Date input = new Date();
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println(date);
		System.out.println("-----------end local date time-----------------");
	}

	@Test
	public void zonedDateTimeTest() {
		// Get the current date and time
		System.out.println("------------start zoned datetime----------------");
		// 东五区时间
		ZonedDateTime date1 = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]");
		System.out.println("date1: " + date1);

		ZoneId id = ZoneId.of("Europe/Paris");
		System.out.println("ZoneId: " + id);

		// 东八区时间
		ZoneId currentZone = ZoneId.systemDefault();
		System.out.println("CurrentZone: " + currentZone);

		System.out.println(date1.toLocalDateTime());
		// 将东五区时间转换为东八区时间
		System.out.println(date1.toInstant().atZone(currentZone).toLocalDateTime());

		System.out.println("---------end zoned datetime------------");
	}

	@Test
	public void chronoUnitsTest() {
		// Get the current date
		LocalDate today = LocalDate.now();
		System.out.println("Current date: " + today);

		// add 1 week to the current date
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		System.out.println("Next week: " + nextWeek);

		// add 1 month to the current date
		LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
		System.out.println("Next month: " + nextMonth);

		// add 1 year to the current date
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		System.out.println("Next year: " + nextYear);

		// add 10 years to the current date
		LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
		System.out.println("Date after ten year: " + nextDecade);
	}

	/**
	 * description: 返回日期之间的间隔
	 * 
	 * @time: 2017年5月5日 上午9:52:04
	 */
	@Test
	public void testPeriod() {

		// Get the current date
		LocalDate date1 = LocalDate.now();
		System.out.println("Current date: " + date1);

		// add 1 month to the current date
		LocalDate date2 = date1.plus(1, ChronoUnit.MONTHS);
		System.out.println("Next month: " + date2);

		Period period = Period.between(date2, date1);
		System.out.println("Period: " + period);
	}

	/**
	 * description: 返回时刻之间的间隔
	 * 
	 * @time: 2017年5月5日 上午9:52:28
	 */
	@Test
	public void testDuration() {
		LocalTime time1 = LocalTime.now();
		Duration twoHours = Duration.ofHours(2);

		LocalTime time2 = time1.plus(twoHours);
		Duration duration = Duration.between(time1, time2);

		System.out.println("Duration: " + duration);
		System.out.println("Duration: " + duration.get(ChronoUnit.SECONDS));
		System.out.println("---------end duration-------------");
	}

	/**
	 * description: TemporalAdjusters主要用于日期计算
	 * 
	 * @time: 2017年5月5日 上午10:15:13
	 */
	@Test
	public void testAdjusters() {

		System.out.println("-----------adjuster test start---------------");
		// Get the current date
		LocalDate date1 = LocalDate.now();
		System.out.println("Current date: " + date1);

		// get the next tuesday
		LocalDate nextTuesday = date1.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
		System.out.println("Next Tuesday on : " + nextTuesday);

		// get the second saturday of next month
		LocalDate firstInYear = LocalDate.of(date1.getYear(), date1.getMonth(), 1);
		LocalDate secondSaturday = firstInYear.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
				.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		System.out.println("Second Saturday on : " + secondSaturday);
		System.out.println("-----------adjuster test end---------------");
	}

	/**
	 * description: 向后兼容性，主要是将java.util.date以及calendar类如何 转换为java.time.*对象
	 * 
	 * @time: 2017年5月5日 上午10:24:12
	 */
	@Test
	public void testBackwardCompatability() {

		// Get the current date
		System.out.println("---------backward compatability start---------");
		Date currentDate = new Date();
		System.out.println("Current date: " + currentDate);

		// Get the instant of current date in terms of milliseconds
		Instant now = currentDate.toInstant();
		ZoneId currentZone = ZoneId.systemDefault();

		LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
		System.out.println("Local date: " + localDateTime);

		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
		System.out.println("Zoned date: " + zonedDateTime);
		System.out.println("---------backward compatability end---------");
	}

}
