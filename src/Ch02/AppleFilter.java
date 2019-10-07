package Ch02;

import java.util.ArrayList;
import java.util.List;

import static Ch02.AppleColor.GREEN;
import static Ch02.AppleColor.RED;

public class AppleFilter {

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterRedApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (RED.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
}
