package Ch04;

import java.util.List;
import java.util.stream.Collectors;

public class S1_Intro {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();
        System.out.println(menu);

        // 4-2
        List<String> threeHighCaloricDishNames =
                menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(threeHighCaloricDishNames);
    }
}
