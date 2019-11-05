package Ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class S04_FunctionalInterfaces {
    class UsingPredicate {
        public <T> List<T> filter(List<T> list, Predicate<T> p) {
            List<T> results = new ArrayList<>();
            for (T t: list) {
                if (p.test(t)) {
                    results.add(t);
                }
            }
            return results;
        }

        public void testFilter() {
            List<String> listOfStrings = new ArrayList<>();
            listOfStrings.add("hahaha");
            listOfStrings.add("");
            listOfStrings.add(null);
            listOfStrings.add("dyhan");
            Predicate<String> nonEmptyStringPredicate = (String s) -> s != null && !s.isEmpty();
            List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
            System.out.println(nonEmpty);
        }
    }

    class UsingConsumer {
        public <T> void forEach(List<T> list, Consumer<T> c) {
            for (T t: list) {
                c.accept(t);
            }
        }

        public void testForEach() {
            forEach(
                    Arrays.asList(1,2,3,4,5),
                    (Integer i) -> System.out.println(i)
            );
        }
    }

    public static void main(String[] args) {
        S04_FunctionalInterfaces proc = new S04_FunctionalInterfaces();

        UsingPredicate test1 = proc.new UsingPredicate();
        test1.testFilter();

        UsingConsumer test2 = proc.new UsingConsumer();
        test2.testForEach();
    }
}
