package Ch11_Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class S4_PracticalExamples {
    public static void main(String[] args) {
        s1141_wrapWithOptional();
        s1142_handleExceptionWithOptional();

        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        System.out.println(5 == readDuration(props, "a"));
        System.out.println(0 == readDuration(props, "b"));
        System.out.println(0 == readDuration(props, "c"));
        System.out.println(0 == readDuration(props, "d"));

        System.out.println(5 == readDuration2(props, "a"));
        System.out.println(0 == readDuration2(props, "b"));
        System.out.println(0 == readDuration2(props, "c"));
        System.out.println(0 == readDuration2(props, "d"));

    }

    private static int readDuration(Properties props, String key) {
//        return Integer.parseInt(props.getProperty(key));
        String value = props.getProperty(key);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch(NumberFormatException e) {

            }
        }
        return 0;
    }

    private static int readDuration2(Properties props, String key) {
        return Optional.ofNullable(props.getProperty(key))
                .flatMap(S4_PracticalExamples::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

    private static void s1141_wrapWithOptional() {
        Map<String, String> map = new HashMap<>();

        // legacy work with nullable java api
        String value = map.get("key");
        System.out.println(value);
        try {
            System.out.println(value.charAt(0));
        } catch (Exception e) {
            System.out.println(e);
        }

        // wrapping with Optional
        Optional<String> optValue = Optional.ofNullable(map.get("Key"));
        optValue.ifPresentOrElse(System.out::println, () -> System.out.println("Empty"));
    }

    private static void s1142_handleExceptionWithOptional() {
        // handling API Exception with Optional
        stringToInt("1234").ifPresentOrElse(System.out::println, () -> System.out.println("0"));
        stringToInt("aaa").ifPresentOrElse(System.out::println, () -> System.out.println("0"));
    }

    private static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }
}
