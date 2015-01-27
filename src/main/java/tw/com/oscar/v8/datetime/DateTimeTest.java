package tw.com.oscar.v8.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * @author oscarwei
 * @since 2015/1/11
 */
public class DateTimeTest {

    public static final String OSCAR = "Oscar";

    public static void main(String[] args) {
        System.out.println(OSCAR);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toLocalDate());
        Month month = now.getMonth();
        System.out.println(month); // return Month object
        System.out.println(now.getDayOfMonth()); // return int
        System.out.println(now.getSecond()); // return int
        LocalDate date = LocalDate.of(2015, Month.JANUARY, 21);
        System.out.println(date);
        LocalDate.ofEpochDay(150);
        LocalTime.of(17, 18);
        LocalTime localTime = LocalTime.parse("10:15:30");
        System.out.println(localTime);
    }
}
