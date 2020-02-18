package Ch12_NewDateTimeAPIs;

import java.time.*;
import java.time.chrono.JapaneseDate;
import java.util.TimeZone;

public class S3_TimeZoneAndCalendar {
    public static void main(String[] args) {
        s1231_TimeZone();
        s1232_UTCStandard();
        // Another Calendars
        LocalDate date = LocalDate.of(2020, 2, 17);
        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }

    private static void s1232_UTCStandard() {
        // UTC / Greenwich standard
        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        System.out.println(newYorkOffset);

        LocalDateTime datetime = LocalDateTime.of(2020, 2, 17, 13, 45);
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(datetime, newYorkOffset);
        System.out.println(dateTimeInNewYork);
    }

    private static void s1231_TimeZone() {
        // using TimeZone
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        System.out.println(romeZone);

        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        System.out.println(zoneId);

        // Zonned Date
        LocalDate date = LocalDate.of(2020, 2, 17);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 27, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);
        ZonedDateTime zdt4 = instant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.printf("%s -> %s\n", date, zdt1);
        System.out.printf("%s -> %s\n", dateTime, zdt2);
        System.out.printf("%s -> %s\n", instant, zdt3);
        System.out.printf("%s -> %s\n", instant, zdt4);

        // convert LocalDateTime to Instant with ZoneId
        instant = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
        System.out.printf("%s, %s\n", instant, timeFromInstant);
        ZonedDateTime zdt5 = timeFromInstant.atZone(romeZone);
        System.out.println(zdt5);  // Wrong : LocalDateTime has no zone information
    }
}
