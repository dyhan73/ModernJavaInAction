package Ch02;

import java.util.ArrayList;
import java.util.List;

import static Ch02.AppleColor.GREEN;
import static Ch02.AppleColor.RED;

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

    }
}
