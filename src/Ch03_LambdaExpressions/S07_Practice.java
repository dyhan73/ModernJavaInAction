package Ch03_LambdaExpressions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class S07_Practice {
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
        inventory.add(new Apple(Color.GREEN, 144));
        inventory.add(new Apple(Color.RED, 134));
        inventory.add(new Apple(Color.GREEN, 66));
        return inventory;
    }

    public class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight().compareTo(o2.getWeight());
        }
    }

    public static void main(String[] args) {
        S07_Practice proc = new S07_Practice();

        List<Apple> inventory = proc.initInventory();
        System.out.println(inventory);

        // Step1 : pass code
        inventory.sort(proc.new AppleComparator());
        System.out.println(inventory);

        // Step2 : Anonymous class
        inventory = proc.initInventory();
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(inventory);

        // Step3 : Lambda
        inventory = proc.initInventory();
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);

        // Step4 : Method Reference
        inventory = proc.initInventory();
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);

    }
}
