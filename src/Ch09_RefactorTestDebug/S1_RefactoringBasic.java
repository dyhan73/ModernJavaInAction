package Ch09_RefactorTestDebug;

import Ch04_IntroducingStreams.Dish;
import Ch06_CollectingDataWithStreams.S3_Grouping.CaloricLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class S1_RefactoringBasic {
    interface Task {
        public void execute();
    }
    static void doSomething(Runnable r) {r.run();}
    static void doSomething(Task a) {a.execute();}

    public static void main(String[] args) {
        s912_AnonymousToLambda();
        s913_UsingMethodReference();
        s914_imperativeProcessToStream();

    }

    private static void s912_AnonymousToLambda() {
        // refactoring to lambda expression from anonymous class
        // simple basic
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("Hello");
            }
        };
        r1.run();

        // same above
        Runnable r2 = () -> System.out.println("hello2");
        r2.run();

        // impossible to use "this", shadow variable
        int a = 10;
        Runnable r3 = () -> {
//            int a = 2;  // compile error (impossible to use shadow variable)
            System.out.println(a);
        };
        r3.run();

        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
        r4.run();

        // lambda occur confusing of overloading (doSomething())
        // using anonymous class
        doSomething(new Task() {
            public void execute() {
                System.out.println("Danger danger!!");
            }
        });

        // using lambda (must use cast operator)
        doSomething((Task) () -> System.out.println("Danger in lambda!!"));
    }

    private static void s913_UsingMethodReference() {
        List<Dish> menu = Dish.getMenu();
        // lambda expression
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(
                        Collectors.groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                );
        System.out.println(dishesByCaloricLevel);

        // using method reference for better readability (must extract lambda logic at Dish class)
        dishesByCaloricLevel = menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));
        System.out.println(dishesByCaloricLevel);

        // using collectors helper methods
        int totalCalories = menu.stream().map(Dish::getCalories)
                .reduce(0, (c1, c2) -> c1 + c2);
        System.out.println(totalCalories);
        // IntelliJ's guide to refactoring
        totalCalories = menu.stream().map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(totalCalories);

        // same above
        totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
        // IntelliJ's guide to refactoring
        totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories);
    }

    private static void s914_imperativeProcessToStream() {
        // replace iteration loop to stream
        List<Dish> menu = Dish.getMenu();
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() > 300) {
                dishNames.add(dish.getName());
            }
        }
        System.out.println(dishNames);
        // stream version (improve readability)
        dishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(dishNames);
    }
}
