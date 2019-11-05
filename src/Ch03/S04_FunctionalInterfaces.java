package Ch03;

import java.util.ArrayList;
import java.util.List;
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

    public static void main(String[] args) {
        S04_FunctionalInterfaces proc = new S04_FunctionalInterfaces();

        UsingPredicate test1 = proc.new UsingPredicate();
        test1.testFilter();
    }
}
