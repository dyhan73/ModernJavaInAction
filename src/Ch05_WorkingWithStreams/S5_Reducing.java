package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class S5_Reducing {
    public static void main(String[] args) {
        S551_SumOfElements();
        S552_MaxMin();
        Q53_CountDish();
    }

    private static void S551_SumOfElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        // old style
        int sum = 0;
        for (int x : numbers) {
            sum += x;
        }
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum2);
        System.out.println(numbers.stream().reduce(0, Integer::sum));
        int mul = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(mul);

        // reduce without initialize
        Optional<Integer> sum3 = numbers.stream().reduce(Integer::sum);
        sum3.ifPresent(System.out::println);

        List<Integer> numbers2 = Arrays.asList(); // empty list
        Optional<Integer> sum4 = numbers2.stream().reduce(Integer::sum);
        sum4.ifPresent(System.out::println);  // no printout
    }

    private static void S552_MaxMin() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        max.ifPresent(System.out::println);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);
    }

    private static void Q53_CountDish() {
        List<Dish> menu = Dish.getMenu();
        int menuCnt = menu.stream().map(d -> 1).reduce(0, Integer::sum);
        System.out.println(menuCnt);

        System.out.println(menu.stream().count());
    }
}
