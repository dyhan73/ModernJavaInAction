package Ch04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S3_StreamVSCollection {
    public static void main(String[] args) {
        // 4-3-1 Once consumed only
        List<String> title = Arrays.asList("Java8", "in", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        // s.forEach(System.out::println); // IllegalStateException

        // Quiz 4-1 : Before Refactoring
        List<Dish> menu = Dish.getMenu();
        System.out.println(menu);

        List<String> highCaloricDishes = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            if (dish.getCalories() > 300) {
                highCaloricDishes.add(dish.getName());
            }
        }
        System.out.println(highCaloricDishes);

        // Answer 4-1
        List<Dish> highCaloricDishes2 = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .collect(Collectors.toList());
        System.out.println(highCaloricDishes2);
    }
}
