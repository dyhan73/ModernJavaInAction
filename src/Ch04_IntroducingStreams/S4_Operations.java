package Ch04_IntroducingStreams;

import java.util.List;
import java.util.stream.Collectors;

public class S4_Operations {
    public static void main(String[] args) {
        // Intermediate operation
        List<Dish> menu = Dish.getMenu();

        List<String> names =
                menu.stream()
                .filter(dish -> {
                    System.out.println("filtering:" + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping:" + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(names);

        /* result (lazy calculation)
            filtering:pork
            mapping:pork
            filtering:beef
            mapping:beef
            filtering:chicken
            mapping:chicken
            [pork, beef, chicken]
         */

        // Terminal operation
        menu.stream().forEach(System.out::println);

        // Quiz 4-2
    }
}
