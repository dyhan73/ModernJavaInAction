package Ch08_CollectionAPIEnhancement;

import org.w3c.dom.ls.LSOutput;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class S3_WorkingWithMap {
    public static void main(String[] args) {
        s831_forEach();
        s832_SortingByKey();
        s833_getOrDefault();
        s834_computePattern();
        s835_removePattern();
        s836_replacePattern();
        s837_Merge();
        q82_usingRemoveIfOfEntrySet();
    }

    private static byte[] calculateDigest(String key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void s831_forEach() {
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);

        // old way
        for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
            String friend = entry.getKey();
            Integer age = entry.getValue();
            System.out.println(friend + " is " + age + " years old");
        }

        // forEach() from java 8
        ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));
    }

    private static void s832_SortingByKey() {
        // sorting methods
        Map<String, String> favoriteMovies = Map.ofEntries(
                Map.entry("Raphael", "Star Wars"),
                Map.entry("Cristina", "Matrix"),
                Map.entry("Olivia", "James Bond")
        );

        favoriteMovies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
//                .forEachOrdered(System.out::println);
                .forEach(System.out::println);
    }

    private static void s833_getOrDefault() {
        // getOrDefault()
        Map<String, String> favoriteMovies = Map.ofEntries(
                Map.entry("Raphael", "Star Wars"),
                Map.entry("Olivia", "James Bond")
        );
        System.out.println(favoriteMovies.getOrDefault("Olivia", "Matrix"));
        System.out.println(favoriteMovies.getOrDefault("Thibaut", "Matrix"));
    }

    private static void s834_computePattern() {
        // compute pattern
        // single value
        List<String> lines = Arrays.asList("abcdefg", "asdfasdf", "kakarott");
        Map<String, byte[]> dataToHash = new HashMap<>();
        lines.forEach(line ->
                dataToHash.computeIfAbsent(line, S3_WorkingWithMap::calculateDigest));
        System.out.println(dataToHash);
        dataToHash.forEach((key, value) -> System.out.println(key + " : " + value));

        // multi values
        // old way
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        String friend = "Raphael";
        List<String> movies = friendsToMovies.get(friend);
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("Star Wars");
        System.out.println(movies);

        // using computeIfAbsent with multi values
        friendsToMovies.computeIfAbsent("Olivia", name -> new ArrayList<>())
                .add("Toy Story");
        System.out.println(friendsToMovies);
    }

    private static void s835_removePattern() {
        // remove pattern
        Map<String, String> favoriteMovies = new HashMap<>();
        favoriteMovies.put("Raphael", "Star Wars");
        favoriteMovies.put("Cristina", "Matrix");
        favoriteMovies.put("Olivia", "James Bond");

        // old version
        boolean result = false;
        String key = "Raphael";
        String value = "Star Wars";
        if (favoriteMovies.containsKey(key) && Objects.equals(favoriteMovies.get(key), value)) {
            favoriteMovies.remove(key);
            result = true;
        }
        System.out.println(result);
        System.out.println(favoriteMovies);

        // new way
        favoriteMovies.remove("Olivia", "Matrix");
        System.out.println(favoriteMovies);
        favoriteMovies.remove("Olivia", "James Bond");
        System.out.println(favoriteMovies);
    }

    private static void s836_replacePattern() {
        // replace pattern
        Map<String, String> favoriteMovies = new HashMap<>();
        favoriteMovies.put("Raphael", "Star Wars");
        favoriteMovies.put("Olivia", "James Bond");
        String movieName = favoriteMovies.get("Raphael");
        System.out.println(movieName);
        favoriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println(movieName);  // replaceAll() replace new object
        System.out.println(favoriteMovies);

        favoriteMovies.replace("Raphael", "Toy Story", "Matrix");
        System.out.println(favoriteMovies);
    }

    private static void s837_Merge() {
        // merge
        // working well without duplicate key
        // but replace final put() value
        Map<String, String> family = Map.ofEntries(
                Map.entry("Teo", "Star Wars"),
                Map.entry("Cristina", "James Bond")
        );
        Map<String, String> friends = Map.ofEntries(
                Map.entry("Raphael", "Star Wars"),
                Map.entry("Cristina", "Matrix")
        );
        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);
        System.out.println(everyone);

        // manage conflict keys (remapping function is working only key and value are exist)
        Map<String, String> everyone2 = new HashMap<>(family);
        friends.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);

        // validate initial value with merge
        Map<String, Long> moviesToCount = new HashMap<>();
        String movieName = "James Bond";
        Long count = moviesToCount.get(movieName);
        if (count == null) {
            moviesToCount.put(movieName, 1L);
        } else {
            moviesToCount.put(movieName, count + 1);
        }
        System.out.println(moviesToCount);

        // same above with merge()
        moviesToCount.merge(movieName, 1L, (key, cnt) -> cnt + 1);
        System.out.println(moviesToCount);
    }

    private static void q82_usingRemoveIfOfEntrySet() {
        // quiz 8-2 : Simplify the code
        // origin
        Map<String, Integer> movies = new HashMap<>();
        movies.put("JamesBond", 20);
        movies.put("Matrix", 15);
        movies.put("Harry Potter", 5);
        Iterator<Map.Entry<String, Integer>> iterator = movies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() < 10) {
                iterator.remove();
            }
        }
        System.out.println(movies);

        // simple solution (better way)
        movies.entrySet().removeIf(entry -> entry.getValue() < 18);
        System.out.println(movies);
    }
}
