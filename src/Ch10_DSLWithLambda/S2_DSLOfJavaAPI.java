package Ch10_DSLWithLambda;

import java.util.*;

public class S2_DSLOfJavaAPI {
    static class Person {
        int age;
        String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name=" + name +
                    '}';
        }

        public static List<Person> getSamples() {
            return Arrays.asList(
                    new Person(30, "bin"),
                    new Person(21, "sooa"),
                    new Person(21, "Han")
            );
        }
    }

    public static void main(String[] args) {
        s1020_simpleDSLwithLambda();
    }

    private static void s1020_simpleDSLwithLambda() {
        List<Person> people = Person.getSamples();
        System.out.println(people);

        // old way
        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println(people);

        // Lambda way
        people = Person.getSamples();
        Collections.sort(people, (p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println(people);

        // method reference
        people = Person.getSamples();
        Collections.sort(people, Comparator.comparing(Person::getAge));
        System.out.println(people);

        // reverse
        Collections.sort(people, Comparator.comparing(Person::getAge).reversed());
        System.out.println(people);

        // sort by age then by name
        Collections.sort(people, Comparator.comparing(Person::getAge).thenComparing(Person::getName));
        System.out.println(people);

        // using default method of List (interface)
        people.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
        System.out.println(people);
    }
}
