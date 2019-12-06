package Ch08_CollectionAPIEnhancement;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S1_CollectionFactory {
    public static void main(String[] args) {
        s810_Intro();
        s811_ListOf();
        s812_SetOf();
        s813_MapOf();
        q81_Immutable();
    }

    private static void s810_Intro() {
        System.out.println("=========== Section 8.1");
        // create list which has a few elements
        List<String> friends = new ArrayList<>();
        friends.add("Raphael");
        friends.add("Olivia");
        friends.add("Thibaut");
        System.out.println(friends);

        // short way using factory methods
        List<String> friends2 = Arrays.asList("Raphael", "Olivia", "Thibaut");
        System.out.println(friends2);

        System.out.println(friends.equals(friends2));

        // add friend
        friends.add("Daeyoung");
        System.out.println(friends);
        // failure to add friend2 which is returned by factory method
        // cause is jra.util.Arrays$ArrayList is fixed length list
//        friends2.add("Daeyoung");
//        System.out.println(friends2);

        // check class
        System.out.println(friends.getClass());   // java.util.ArrayList
        System.out.println(friends2.getClass());  // java.util.Arrays$ArrayList

        // modify friend (it's fine)
        friends.set(0, "kakarott");
        System.out.println(friends);
        friends2.set(0, "kakarott");
        System.out.println(friends2);

        // how about Set?
        Set<String> friendSet = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));
        // or
        Set<String> friendSet2 = Stream.of("Raphael", "Olivia", "Thibaut").collect(Collectors.toSet());
        System.out.println(friendSet);
        System.out.println(friendSet2);
        System.out.println(friendSet.equals(friendSet2));
        System.out.println(friendSet.getClass());
        System.out.println(friendSet2.getClass());
    }

    private static void s811_ListOf() {
        System.out.println("========== Section 8.1.1");
        // using List.of()
        List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends);
        System.out.println(friends.getClass());  // java.util.ImmutableCollections$ListN
//        friends.set(0, "kakarott"); // failure
//        System.out.println(friends);
//        friends.add("Daeyoung");  // failure
//        System.out.println(friends);
    }

    private static void s812_SetOf() {
        System.out.println("========== Section 8.1.2");
        // using Set.of()
        Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends);
        System.out.println(friends.getClass());  //java.util.ImmutableCollections$SetN

        friends = Set.of("Raphael", "Olivia", "Thibaut", "4", "5", "6", "7", "8", "9", "10", "11");
        System.out.println(friends);
        System.out.println(friends.getClass());  // same above even with over 10 arguments

//        friends = Set.of("Raphael", "Olivia", "Olivia");  // failure - duplicate element
//        System.out.println(friends);
    }

    private static void s813_MapOf() {
        System.out.println("=========== Section 8.1.3");
        // Map.of()
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        System.out.println(ageOfFriends);
        System.out.println(ageOfFriends.getClass());  //java.util.ImmutableCollections$MapN

        // Recommended way with over than 10 elements
        ageOfFriends = Map.ofEntries(
                Map.entry("Raphael", 30),
                Map.entry("Olivia", 25),
                Map.entry("Thibaut", 26));
        System.out.println(ageOfFriends);
        System.out.println(ageOfFriends.getClass());
    }

    private static void q81_Immutable() {
        System.out.println("=========== Quiz 8-1");
        List<String> actors = List.of("Keanu", "Jessica");
        try {
            actors.set(0, "Brad");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(actors);
    }
}
