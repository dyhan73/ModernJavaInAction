package Ch12_NewDateTimeAPIs;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Locale;

public class S2_DateAdjustParsingFormatting {
    public static void main(String[] args) {
        s1220_adjustDate();
        q121_adjustLocalDate();
        s1221_usingTemporalAdjusters();
        q122_customTemporalAdjuster();
        s1222_formatAndParse();

    }

    public static class NextWorkingDay implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }

    }
    private static void s1220_adjustDate() {
        // adjust with .withAttribute which return new object instead of change the original (to support immutable)
        LocalDate date1 = LocalDate.of(2020, 2, 17);
        LocalDate date2 = date1.withYear(2018);
        LocalDate date3 = date2.withDayOfMonth(21);
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 5);
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);

        // relative way
        date2 = date1.plusWeeks(1);
        date3 = date2.minusYears(6);
        date4 = date3.plus(6, ChronoUnit.MONTHS);
        System.out.println(String.format("%s, %s, %s, %s", date1, date2, date3, date4));
    }
    private static void q121_adjustLocalDate() {
        // quiz : date value?
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(ChronoField.MONTH_OF_YEAR, 9);
        date = date.plusYears(2).minusDays(10);
        date.withYear(2011);  // date is not adjust (immutable)

        System.out.println(date);  // 2016-09-08
    }

    private static void s1221_usingTemporalAdjusters() {
        // using TemporalAdjusters
        LocalDate date1 = LocalDate.of(2020, 2, 17);
        LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date3 = date2.with(TemporalAdjusters.lastDayOfMonth());
        System.out.printf("%s, %s, %s\n", date1, date2, date3);  // 2020-02-17, 2020-02-23, 2020-02-29
    }

    private static void q122_customTemporalAdjuster() {
        // implement NextWorkingDay class (custom TemporalAdjuster)
        // - get next working (skip Sat. Sun.)
        LocalDate date = LocalDate.of(2020, 2, 17);
        for (int i=0; i<5; i++) {
            date = date.with(temporal -> {
                DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                int dayToAdd = 1;
                if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                return temporal.plus(dayToAdd, ChronoUnit.DAYS);
            });
            System.out.println(date);
        }

        date = LocalDate.of(2020, 2, 21);
        date = date.with(new NextWorkingDay());
        System.out.println(date);
    }

    private static void s1222_formatAndParse() {
        // java.time.format
        LocalDate date = LocalDate.of(2020, 2, 17);
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s1);  // 20200217
        System.out.println(s2);  // 2020-02-17

        date = LocalDate.parse("20200318", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(date);
        date = LocalDate.parse("2020-02-17", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(date);

        // pattern formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.of(2020, 2, 17);
        String formattedDate = date1.format(formatter);
        System.out.println(formattedDate);
        LocalDate date2 = LocalDate.parse(formattedDate, formatter);
        System.out.println(date2);

        // pattern with locale
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        date1 = LocalDate.of(2020, 2, 17);
        formattedDate = date1.format(italianFormatter);
        System.out.println(formattedDate);
        date2 = LocalDate.parse(formattedDate, italianFormatter);
        System.out.println(date2);

        italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.KOREAN);
        date1 = LocalDate.of(2020, 2, 17);
        formattedDate = date1.format(italianFormatter);
        System.out.println(formattedDate);
        date2 = LocalDate.parse(formattedDate, italianFormatter);
        System.out.println(date2);

        // using DateTimeFormatterBuilder
        DateTimeFormatter italianFormatter2 = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
        System.out.println(date1.format(italianFormatter2));
    }
}
