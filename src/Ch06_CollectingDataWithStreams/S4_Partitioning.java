package Ch06_CollectingDataWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.*;
import java.util.stream.IntStream;
//import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class S4_Partitioning {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();
        s640_Intro(menu);
        s641_AdvantagesOfPartitioning(menu);
        q62_UsePartitioningBy(menu);
        s642_PartitioningNumberByPrime();
    }

    private static void s640_Intro(List<Dish> menu) {
        // Partitioning by predication (true/false)
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMenu);

        // get vegetarian dishes
        List<Dish> vegetarianDishes = partitionedMenu.get(true);
        System.out.println(vegetarianDishes);
        // same code using filter
        vegetarianDishes = menu.stream().filter(Dish::isVegetarian).collect(toList());
        System.out.println(vegetarianDishes);
    }

    private static void s641_AdvantagesOfPartitioning(List<Dish> menu) {
        // partitioning returned both result (true and false)
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType))
        );
        System.out.println(vegetarianDishesByType);

        // get max calorie dish of each group
        Map<Boolean, Dish> mostCaloricDishByVegetarian = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get))
        );
        System.out.println(mostCaloricDishByVegetarian);
    }

    private static void q62_UsePartitioningBy(List<Dish> menu) {
        // 1
        Map<Boolean, Map<Boolean, List<Dish>>> heavyDishByVegetarian = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        partitioningBy(d -> d.getCalories() > 500))
        );
        System.out.println(heavyDishByVegetarian);

        // 2
        Map<Boolean, Map<Dish.Type, List<Dish>>> dishByVegetarianAndType = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType))  // partitioning is not working with enum (only with predicate)
        );
        System.out.println(dishByVegetarianAndType);

        // 3
        Map<Boolean, Long> countsByVegetarian = menu.stream().collect(
                partitioningBy(Dish::isVegetarian, counting()));
        System.out.println(countsByVegetarian);
    }

    private static void s642_PartitioningNumberByPrime() {
        int candidate = 40;
        System.out.println(isPrime(candidate));
        candidate = 41;
        System.out.println(isPrime2(candidate));

        List<Integer> intList = Arrays.asList(11, 12, 13, 14, 15, 16, 17);
        Map<Boolean, List<Integer>> partByIsPrime = intList.stream().collect(
                partitioningBy(S4_Partitioning::isPrime2)
        );
        System.out.println(partByIsPrime);
        return;
    }

    private static boolean isPrime(int candidate) {
        return IntStream.range(2, candidate)
                .noneMatch(i -> candidate % i == 0);
    }

    // improved version by less than square root
    private static boolean isPrime2(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.range(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}
