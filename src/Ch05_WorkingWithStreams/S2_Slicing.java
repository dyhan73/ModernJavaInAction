package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class S2_Slicing {
    public static void main(String[] args) {
        S521_ByPredicate();
        S522_Truncating();
        Q51_Filtering();
    }

    private static void S521_ByPredicate() {
        // 5.2.1 By predicate
        List<Dish> menu = Dish.getMenu();
        List<String> ll = menu.stream().map(d -> d.getName() + d.getCalories()).collect(Collectors.toList());
        System.out.println(ll);

        // limit by calories using filter
        List<Dish> filteredMenu = menu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        System.out.println(filteredMenu);

        // if sorted by calories already
        List<Dish> sorted = menu.stream().sorted(Comparator.comparing(Dish::getCalories)).collect(Collectors.toList());
        System.out.println(sorted.stream().map(Dish::getCalories).collect(Collectors.toList()));

        // Using takeWhile
        List<Dish> slicedMenu1 = sorted.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        System.out.println(slicedMenu1);

        // also using dropWhile
        List<Dish> slicedMenu2 = sorted.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        System.out.println(slicedMenu2);
    }

    private static void S522_Truncating() {
        // using limit()
        List<Dish> menu = Dish.getMenu();
        List<Dish> dishes = menu.stream()
                .filter(dish -> dish.getCalories() > 400)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(dishes);

        // skip
        List<Dish> skippedMenu = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(3)
                .collect(Collectors.toList());
        System.out.println(skippedMenu);
    }

    private static void Q51_Filtering() {
        List<Dish> menu = Dish.getMenu();
        List<Dish> meatMenu = menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(meatMenu);
    }
}
