package Ch05_WorkingWithStreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.*;

public class S8_BuildingStreams {
    public static void main(String[] args) {
        s581_FromValues();
        s582_StreamFromNullable();
        test_system_getproperty();
        s583_ArrayToStream();
        s584_StreamFromFile();
        s585_StreamFromFunction();
    }

    private static void s581_FromValues() {
        // using Stream.of()
        Stream<String> stream = Stream.of("Modern ", "Java ", "in ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);
    }

    private static void s582_StreamFromNullable() {
        // until java 8
        String homeValue = System.getProperty("java.version");
        Stream<String> homeValueStream =
                homeValue == null ? Stream.empty() : Stream.of(homeValue);
        homeValueStream.forEach(System.out::println);

        // after java 9
        Stream<String> homeValueStream2 =
                Stream.ofNullable(System.getProperty("java.version"));
        homeValueStream2.forEach(System.out::println);

        // Nullable with flatMap
        Stream<String> values =
                Stream.of("config", "home", "user")
                        .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
        values.forEach(System.out::println);
    }

    private static void test_system_getproperty() {
        Properties props = System.getProperties();
        for(Enumeration en = props.propertyNames(); en.hasMoreElements();) {
            String key = (String)en.nextElement();
            String value = props.getProperty(key);
            // System.out.println(key + "=" + value);
        }

        Stream<String> propertiesKeys = Stream.of(props.keys().toString());
        propertiesKeys.forEach(System.out::println);
    }

    private static void s583_ArrayToStream() {
        int[] numbers = {2, 3, 5, 7};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);
    }

    private static void s584_StreamFromFile() {
        long uniqueWords = 0;
        try(Stream<String> lines =
                    Files.lines(Paths.get("/etc/hosts"))) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(uniqueWords);
    }

    private static void s585_StreamFromFunction() {
        // iterate()
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
        q54_FibonacciNumbers();

        // iterate() predicated version (java 9+)
        Stream.iterate(0, n -> n < 20, n -> n + 4)
                .forEach(System.out::println);

        // generate()
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        IntStream ones = IntStream.generate(() -> 1);
        ones.limit(5).forEach(System.out::println);

        // generate() with anonymous class
        IntStream twos = IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        });
        twos.limit(3).forEach(System.out::println);

        // generate() with status (using anonymous class) - IT OCCURS SIDE-EFFECT. Don't DO this!
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldPrevious = previous;
                int newVal = previous + current;
                previous = current;
                current = newVal;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }

    private static void q54_FibonacciNumbers() {
        // Fibonachi numbers : 0 1 1 2 3 5 8 13 21 ...
        // Fibonachi set : (0,1), (1,1), (1,2), (2,3) ...
        Stream.iterate(new int[]{0, 1}, fs -> new int[]{fs[1], fs[0] + fs[1]})
                .limit(10)
                .forEach(fs -> System.out.println("(" + fs[0] + ", " + fs[1] + ")"));
    }
}
