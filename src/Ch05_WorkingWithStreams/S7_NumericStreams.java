package Ch05_WorkingWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.List;

public class S7_NumericStreams {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();

        s570_Intro(menu);

    }

    private static void s570_Intro(List<Dish> menu) {
        // sum of calories
        int sumCalories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(sumCalories);
        // map() return Stream<Integer>
        // we can't use .sum() because return type is not Integer
        // => It's why primitive stream specializations are offered (to using methods of primitive type)
    }
}
