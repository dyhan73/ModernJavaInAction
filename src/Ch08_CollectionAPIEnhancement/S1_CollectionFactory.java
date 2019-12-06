package Ch08_CollectionAPIEnhancement;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S1_CollectionFactory {
    public static void main(String[] args) {
        s810_Intro();

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
}
