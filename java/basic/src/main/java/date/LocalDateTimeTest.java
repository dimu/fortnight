package date;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * LocalDateTime测试
 */
public class LocalDateTimeTest {

    @Test
    public void convertLocalDateToDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        Instant instant = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant();
        Date date  = Date.from(instant);
        System.out.println(date);

    }
}
