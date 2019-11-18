package Ch02_PassingCodeWithBehaviorParameterization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AppleFilter {

//    public static List<Apple> filterGreenApples(List<Apple> inventory) {
//        List<Apple> result = new ArrayList<>();
//        for (Apple apple: inventory) {
//            if (GREEN.equals(apple.getColor())) {
//                result.add(apple);
//            }
//        }
//        return result;
//    }
//
//    public static List<Apple> filterRedApples(List<Apple> inventory) {
//        List<Apple> result = new ArrayList<>();
//        for (Apple apple: inventory) {
//            if (RED.equals(apple.getColor())) {
//                result.add(apple);
//            }
//        }
//        return result;
//    }

//    public static List<Apple> filterApplesByColor(List<Apple> inventory, AppleColor color) {
//        List<Apple> result = new ArrayList<>();
//        for (Apple apple: inventory) {
//            if (color.equals(apple.getColor())) {
//                result.add(apple);
//            }
//        }
//        return result;
//    }
//
//    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
//        List<Apple> result = new ArrayList<>();
//        for (Apple apple: inventory) {
//            if (weight <= apple.getWeight()) {
//                result.add(apple);
//            }
//        }
//        return result;
//    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // abstract to list
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e: list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}
