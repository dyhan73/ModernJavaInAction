package Ch12_NewDateTimeAPIs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class S0_OldWays {
    public static void main(String[] args) {

        // set Date of 2027.09.21
        Date date = new Date(117, 8, 21);
        System.out.println(date);

        // Calendar from java 1.1
        Calendar calendar = new Calendar.Builder()
                .setDate(2020, 8, 17)
                .build();
        System.out.println(calendar);

        // DateFormat
        DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        System.out.println(df.format(new Date()));
        System.out.println(df.format(date));

        // programmer commonly use Joda-Time library
    }
}
