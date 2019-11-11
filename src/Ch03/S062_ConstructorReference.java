package Ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
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

    List<Color> colors = Arrays.asList(Color.RED, Color.RED, Color.GREEN, Color.GREEN);
    List<Apple> apples2 = map2(colors, weights, Apple::new);
    public List<Apple> map2(List<Color> cList, List<Integer> wList, BiFunction<Color, Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (int i=0; i<cList.size(); i++) {
            result.add(f.apply(cList.get(i), wList.get(i)));
        }
        return result;
    }
    public static void main(String[] args) {
        S062_ConstructorReference proc = new S062_ConstructorReference();
        System.out.println(proc.apples);
        System.out.println(proc.apples2);
    }
}
