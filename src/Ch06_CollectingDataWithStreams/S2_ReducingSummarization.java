package Ch06_CollectingDataWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.*;
import java.util.stream.Collectors;

public class S2_ReducingSummarization {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();
        s621_FindMaxMinValues(menu);
        s622_Summarization(menu);
        s623_StringJoining(menu);
        // sum calories using general reducing
        int totalCalories = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories);
        totalCalories = menu.stream().map(Dish::getCalories).reduce(0, (i, j) -> i + j);
        System.out.println(totalCalories);
        totalCalories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        System.out.println(totalCalories);

        // find most calorie dish
        Optional<Dish> mostCalorieDish = menu.stream().collect(Collectors.reducing(
                (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2
        ));
        System.out.println(mostCalorieDish);
        mostCalorieDish = menu.stream().reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2);
        System.out.println(mostCalorieDish);
    }

    private static void s621_FindMaxMinValues(List<Dish> menu) {
        // count dishes of menu
        long howManyDishes = menu.stream().collect(Collectors.counting());
        System.out.println(howManyDishes);
        howManyDishes = menu.stream().count();
        System.out.println(howManyDishes);

        // find max & min values (calories of dish)
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(Collectors.maxBy(dishCaloriesComparator));
        mostCalorieDish.ifPresent(System.out::println);
    }

    private static void s622_Summarization(List<Dish> menu) {
        // Summary operations
        // Sum
        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
        // Average
        double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(avgCalories);
        // Statistics
        IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);
    }

    private static void s623_StringJoining(List<Dish> menu) {
        // String join
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(shortMenu);

        // simplify by toString => error (Type error)
//        shortMenu = (String) menu.stream().collect(Collectors.joining());
//        System.out.println(shortMenu);
    }
}
