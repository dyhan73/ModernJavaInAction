package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S3_Mapping {
    public static void main(String[] args) {
        S531_Intro();
        S532_FlatMap();
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

    private static void S532_FlatMap() {
        // using split (not working because split return type is String[]
        List<String> words = Arrays.asList("Hello", "World");
        List<String[]> chars = words.stream()
                .map(w -> w.split(""))
                .distinct()
                .collect(Collectors.toList());
        for (String[] sa : chars) {
            for (String s : sa)
            System.out.println(s);
        }

        // using map & Arrays.stream
        String[] arrayOfWord = {"Goodbye", "World"};
        Stream<String> streamOfWord = Arrays.stream(arrayOfWord);
        System.out.println(streamOfWord.collect(Collectors.joining()));

        List<Stream<String>> chars2 = words.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        // using flatMap (Flatting nested list)
        List<String> uniqueCharacters = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueCharacters);
    }
}
