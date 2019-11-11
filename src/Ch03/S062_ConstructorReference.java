package Ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class S062_ConstructorReference {
    enum Color {GREEN, RED}
    class Apple {
        Color color;
        int weight;

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
    }

    List<Integer> weights = Arrays.asList(7, 3, 4, 10);
    List<Apple> apples = map(weights, Apple::new);
    public List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer i: list) {
            result.add(f.apply(i));
        }
        return result;
    }
    public static void main(String[] args) {
        S062_ConstructorReference proc = new S062_ConstructorReference();
        System.out.println(proc.apples);
    }
}
