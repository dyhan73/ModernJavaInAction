package Ch02_PassingCodeWithBehaviorParameterization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Ch02_PassingCodeWithBehaviorParameterization.AppleColor.GREEN;
import static Ch02_PassingCodeWithBehaviorParameterization.AppleColor.RED;

public class TestFilter {
    public static void main(String[] args) {
        ArrayList<Apple> inventory = new ArrayList<>();
        inventory.add(new Apple(GREEN, 88));
        inventory.add(new Apple(RED, 166));
        inventory.add(new Apple(GREEN, 140));
        inventory.add(new Apple(GREEN, 160));
        inventory.add(new Apple(RED, 88));
        inventory.add(new Apple(GREEN, 122));
        inventory.add(new Apple(GREEN, 177));

//        List<Apple> result = AppleFilter.filterGreenApples(inventory);
//        System.out.println(result);
//
//        result = AppleFilter.filterRedApples(inventory);
//        System.out.println(result);

//        List<Apple> result = AppleFilter.filterApplesByColor(inventory, GREEN);
//        System.out.println(result);
//
//        result = AppleFilter.filterApplesByWeight(inventory, 150);
//        System.out.println(result);

        List<Apple> result = AppleFilter.filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println(result);

        result = AppleFilter.filterApples(inventory, new AppleHeavyWeightPredicate());
        System.out.println(result);

        result = AppleFilter.filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(result);

        // Anonymous class
        result = AppleFilter.filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return AppleColor.RED.equals(apple.getColor());
            }
        });
        System.out.println(result);

        // lambda expression
        result = AppleFilter.filterApples(inventory, (Apple apple) -> AppleColor.RED.equals(apple.getColor()));
        System.out.println(result);

        // abstraction to list
        result = AppleFilter.filter(inventory, (Apple apple) -> AppleColor.RED.equals(apple.getColor()));
        System.out.println(result);

        List<Integer> intInven = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Integer> evenNums = AppleFilter.filter(intInven, (Integer i) -> i % 2 == 0);
        System.out.println(evenNums);

    }
}
