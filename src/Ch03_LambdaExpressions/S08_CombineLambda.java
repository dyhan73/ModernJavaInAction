
package Ch03_LambdaExpressions;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * S08_CombineLambda
 */
public class S08_CombineLambda {
    enum Color {GREEN, RED}

    class Apple {
        Color color;
        Integer weight;

        Apple(int weight) {
            this.color = Color.GREEN;
            this.weight = weight;
        }

        Apple(Color color, int weight) {
            this.color = color;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return this.color + "(" + this.weight + ")";
        }

        public Color getColor() {
            return color;
        }

        public Integer getWeight() {
            return weight;
        }
    }

    public List<Apple> initInventory() {
        List<Apple> inventory = new ArrayList<>();
        inventory.add(new Apple(Color.RED, 44));
        inventory.add(new Apple(Color.RED, 144));
        inventory.add(new Apple(Color.RED, 134));
        inventory.add(new Apple(Color.GREEN, 66));
        inventory.add(new Apple(Color.GREEN, 144));
        return inventory;
    }

    public static void main(String[] args) {
        S08_CombineLambda proc = new S08_CombineLambda();
        List<Apple> inventory = proc.initInventory();
        System.out.println("Original : " + inventory);

        // sort
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println("- sort : " + inventory);

        // reverse sort
        inventory = proc.initInventory();
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed());
        System.out.println("- reverse sort : " + inventory);

        // second sort order
        inventory = proc.initInventory();
        inventory.sort(Comparator.comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor));
        System.out.println("- sort weight then color" + inventory);

        // Predicate
        List<Apple> redApples = inventory.stream().filter((a) -> a.getColor() == Color.RED).collect(Collectors.toList());
        System.out.println(redApples);

        Predicate<Apple> redApple = (a) -> a.getColor() == Color.RED;
        inventory.removeIf(redApple);
        System.out.println(inventory);

        inventory = proc.initInventory();
        inventory.removeIf(redApple.negate());
        System.out.println(inventory);

        // Combine Predicate
        Predicate<Apple> heavyRedApple = (a) -> a.getWeight() > 100;
        heavyRedApple = heavyRedApple.and((a) -> a.getColor() == Color.RED);
        inventory = proc.initInventory();
        inventory.removeIf(heavyRedApple);
        System.out.println(inventory);

        // Function

    }

}