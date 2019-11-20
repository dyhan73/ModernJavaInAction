package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class S7_NumericStreams {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();

        s570_Intro(menu);
        s571_NumericSpecialization(menu);
        s572_NumericRanges();
        s573_Practice_PythagoreanTriples();
    }

    private static void s570_Intro(List<Dish> menu) {
        // sum of calories
        int sumCalories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(sumCalories);
        // map() return Stream<Integer>
        // we can't use .sum() because return type is not Integer
        // => It's why primitive stream specializations are offered (to using methods of primitive type)
    }

    private static void s571_NumericSpecialization(List<Dish> menu) {
        // Specialization
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println(calories);

        // Back to stream
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();

        stream.forEach(System.out::println);

        // OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        System.out.println(maxCalories.getAsInt());

        List<Dish> emptyMenu = Arrays.asList();
        OptionalInt maxCalories2 = emptyMenu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        maxCalories2.ifPresent(System.out::println); // not presented
    }

    private static void s572_NumericRanges() {
        // .range(s, f) => s, s+1, ... f-1
        // .rangeClosed(s, f) => s, s+1, ... f-1, f
        IntStream evenNumbers = IntStream.range(1, 100)
                .filter(n -> n % 2 == 1);
//        System.out.println(evenNumbers.count());
        System.out.println(evenNumbers.min());
//        System.out.println(evenNumbers.max());
    }

    private static void s573_Practice_PythagoreanTriples() {
        // Pythagorean Triples : a^2 + b^2 = c^2
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)})
        );
        pythagoreanTriples.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        // enhancement
        // pros: reduce sqrt calculation
        // cons: must using double[]
        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                    .mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
                    .filter(t -> t[2] % 1 == 0));
        pythagoreanTriples2.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}
