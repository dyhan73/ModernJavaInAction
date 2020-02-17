package Ch12_NewDateTimeAPIs;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class S1_JavaTimePackage {
    public static void main(String[] args) {
        s1211_LocalDateLocalTime();
        s1212_usingLocalDateTime();
        s1213_InstantClass();
        s1214_DurationAndPeriod();
    }

    private static void s1211_LocalDateLocalTime() {
        // LocalDate
        LocalDate date = LocalDate.of(2020, 2, 17);
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int len = date.lengthOfMonth();
        boolean leap = date.isLeapYear();

        System.out.println(String.format("%d, %s, %d, %s, %d, %b", year, month, day, dayOfWeek, len, leap));

        // get current date
        LocalDate today = LocalDate.now();
        System.out.println(today);

        // using TemporalField
        int year1 = date.get(ChronoField.YEAR);
        int month1 = date.get(ChronoField.MONTH_OF_YEAR);
        int day1 = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println(String.format("%d-%d-%d", year1, month1, day1));

        // get values with internal methods
        System.out.println(String.format("%d-%d-%d", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));

        // using LocalTime
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println(String.format("%d:%d:%d", hour, minute, second));

        // set from string by paese()
        LocalDate date1 = LocalDate.parse("2020-02-17");
        LocalTime time1 = LocalTime.parse("11:01:15");
        System.out.println(date1);
        System.out.println(time1);
    }

    private static void s1212_usingLocalDateTime() {
        // LocalDateTime (combine Date & Time)
        LocalDateTime dt = LocalDateTime.of(2020, Month.FEBRUARY, 17, 11, 22, 19);
        System.out.println(dt);
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().atTime(11, 22, 19));
        System.out.println(LocalDate.now().atTime(LocalTime.now()));
        System.out.println(LocalTime.now().atDate(LocalDate.now()));

        dt = LocalDateTime.now();
        System.out.println(dt.toLocalDate());
        System.out.println(dt.toLocalTime());
    }

    private static void s1213_InstantClass() {
        // Instant class (based on Unix epoch time)
        Instant instant = Instant.ofEpochSecond(3);
        System.out.println(instant);
        System.out.println(Instant.ofEpochSecond(3, 0));
        System.out.println(Instant.ofEpochSecond(2, 1_000_000_000));
        System.out.println(Instant.ofEpochSecond(4, -1_000_000_000));
        System.out.println(Instant.ofEpochSecond(3, 90_000_000));

        // working with zoneId
        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.of("+9")));
        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul")));

        // Instant not support to human readability
        try {
            int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
            System.out.println(day);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void s1214_DurationAndPeriod() {
        // Duration
        LocalTime lt1 = LocalTime.parse("11:20:19");
        LocalTime lt2 = LocalTime.parse("12:14:04");
        Duration d1 = Duration.between(lt1, lt2);
        System.out.println(d1);  // PT53M45S

        LocalDateTime ldt1 = LocalDateTime.now();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime ldt2 = LocalDateTime.now();
        Duration d2 = Duration.between(ldt1, ldt2);
        System.out.println(d2);  // PT2.000295
        System.out.println(d2.getSeconds());  // 2

        Instant ins1 = Instant.ofEpochSecond(4, 9_000_000);
        Instant ins2 = Instant.ofEpochSecond(5, 4_000);
        Duration d3 = Duration.between(ins1, ins2);
        System.out.println(d3);  // PT0.991004S

        // Duration with LocalDate (unsupported Temporal Type Exception)
        LocalDate ld1 = LocalDate.of(2020, 2, 17);
        LocalDate ld2 = LocalDate.of(2020, 2, 20);
        try {
            // exception due to Duration handles seconds and nano-seconds (LocalDate has no seconds information)
            Duration d4 = Duration.between(ld1, ld2);
            System.out.println(d4);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Period (well working with LocalDate
        Period tenDays = Period.between(ld1, ld2);
        System.out.println(tenDays);  // P3D

        // Factory methods
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(String.format("%s, %s", threeMinutes, threeMinutes2));  // PT3M, PT3M

        Period tenDays1 = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println(String.format("%s, %s, %s", tenDays1, threeWeeks, twoYearsSixMonthsOneDay));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inc15seconds = now.plus(tenDays1);
        System.out.println(String.format("%s, %s", now, inc15seconds)); // 2020-02-17T15:58:17.281114, 2020-02-27T15:58:17.281114
    }
}
