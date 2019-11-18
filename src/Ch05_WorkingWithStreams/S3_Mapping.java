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
        Q52_Mapping();
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

    private static void Q52_Mapping() {
        // Input : number list
        // Output : squared number list
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> squaredNumbers = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println(squaredNumbers);

        // Input : two number list
        // Output : Cardinal map
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        pairs.stream().forEach(p -> System.out.println(p[0] + ", " + p[1]));

        // input : tow number list
        // output : cardinal map filtered by sum % 3 = 0
        List<int[]> pairs2 = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .filter(p -> (p[0] + p[1]) % 3 == 0)
                .collect(Collectors.toList());
        pairs2.stream().forEach(p -> System.out.println(p[0] + ", " + p[1]));
    }
}
