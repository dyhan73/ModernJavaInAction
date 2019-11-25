package Ch06_CollectingDataWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.*;
import java.util.stream.Collectors;

public class S3_Grouping {
    public enum CaloricLevel { DIET, NORMAL, FAT }

    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();

        s630_Intro(menu);
        s631_ManipulatingGroupedElements(menu);
    }

    private static void s630_Intro(List<Dish> menu) {
        // group by Type
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // group by CaloricLevel
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy( dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);
    }

    private static void s631_ManipulatingGroupedElements(List<Dish> menu) {
        // filtering before groupBy
        // => Type.FISH is disappeared
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(Collectors.groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);

        // using groupBy which has filter parameter
        // => Type.FISH is empty but included
        caloricDishesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));
        System.out.println(caloricDishesByType);

        // get names using groupBy which has map function parameter
        Map<Dish.Type, List<String>> dishNamesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        System.out.println(dishNamesByType);

        // get tags
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("tasty", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));

        Map<Dish.Type, Set<String>> dishNamesSetByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(), Collectors.toSet())));
        System.out.println(dishNamesSetByType);
    }
}
