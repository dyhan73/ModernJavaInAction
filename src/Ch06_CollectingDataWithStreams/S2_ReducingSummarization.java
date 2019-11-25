package Ch06_CollectingDataWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S2_ReducingSummarization {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();
        s621_FindMaxMinValues(menu);
        s622_Summarization(menu);
        s623_StringJoining(menu);
        s624_GeneralizedSummarizationReducing(menu);
        s624_CollectVsReduce();
        q61_StringJoinWithReduce(menu);
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

    private static void s624_GeneralizedSummarizationReducing(List<Dish> menu) {
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

        totalCalories = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        System.out.println(totalCalories);

        totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories);
    }

    private static void s624_CollectVsReduce() {
        // collect version
        // => using for change container
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        System.out.println(stream.collect(Collectors.toList()));

        // reduce version (wrong example due to side-effect : modify input data)
        stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e); // wrong usage
                    return l;
                },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2); // wrong usage
                    return l1;
                }
        );
        System.out.println(numbers);
    }

    private static void q61_StringJoinWithReduce(List<Dish> menu) {
        String menuNames = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(menuNames);

        // implement joining
        menuNames = menu.stream().map(Dish::getName).collect(Collectors.reducing((s1, s2) -> s1 + ", " + s2)).get();
        System.out.println(menuNames);
        // check Optional
        Optional<String> optMenuNames = menu.stream().map(Dish::getName).collect(Collectors.reducing((s1, s2) -> s1 + ", " + s2));
        optMenuNames.ifPresent(System.out::println);
        // BiFunction must return same TYPE of parameter. It doesn't work
//        menuNames = menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getName() + d2.getName())).get();
        menuNames = menu.stream().collect(Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + ", " + s2));
        System.out.println(menuNames);
    }
}
