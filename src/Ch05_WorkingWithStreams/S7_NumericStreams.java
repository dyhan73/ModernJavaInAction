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
}
