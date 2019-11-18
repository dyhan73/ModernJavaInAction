package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class S4_Finding {
    public static void main(String[] args) {
        S541_BasicOperations();
        S543_FindingElements();
        S544_FindingFirstElement();
    }

    private static void S541_BasicOperations() {
        // anyMatch
        List<Dish> menu = Dish.getMenu();
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The Menu is (somewhat) vegetarian friendly!");
        }

        // allMatch
        boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        if (isHealthy) {
            System.out.println("All menu are healthy (under 1000 calories)");
        }

        // nonMatch
        if (menu.stream().noneMatch(d -> d.getCalories() >= 1000)) {
            System.out.println("There is no over calorie menu.");
        }
    }

    private static void S543_FindingElements() {
        List<Dish> menu = Dish.getMenu();
        System.out.println(menu);
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .filter(d -> d.getCalories() > 800)
                .findAny();
        System.out.println(dish);

        // Optional methods (ifPresent, isPresent, get, orElse)
        menu.stream()
                .filter(Dish::isVegetarian)
                .filter(d -> d.getCalories() > 500)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));
    }

    private static void S544_FindingFirstElement() {
        // get number which is no remainders divided by 3
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Optional<Integer> firstSquareDivisibleByThree =
                numbers.parallelStream()
                .map(n -> n*n)
                .filter(n -> n % 3 == 0)
                .findFirst();
        firstSquareDivisibleByThree.ifPresent(System.out::println);
    }
}
