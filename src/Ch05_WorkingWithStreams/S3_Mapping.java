package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class S3_Mapping {
    public static void main(String[] args) {
        S531_Intro();
    }

    private static void S531_Intro() {
        List<Dish> menu = Dish.getMenu();
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(dishNames);

        // get character counts of words
        List<String> words = Arrays.asList("Modern", "Java", "in", "action");
        System.out.println(words);
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(wordLengths);

        // get character length of menu
        List<Integer> menuNameLength = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(menuNameLength);
    }
}
