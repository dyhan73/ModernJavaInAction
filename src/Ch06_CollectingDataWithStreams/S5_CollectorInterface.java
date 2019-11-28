package Ch06_CollectingDataWithStreams;

import Ch04_IntroducingStreams.Dish;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class S5_CollectorInterface {

    // toList implementation of Collector
    static class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

        @Override
        public Supplier<List<T>> supplier() {
            // must return empty result
            return ArrayList::new;  // () -> new ArrayList<T>();
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            // return reducing function
            return List::add;  // (list, item) -> list.add(item);
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity(); // return function itself without other operation
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            // combine result of parallel processing
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
//            Set<Characteristics> characteristics = new HashSet<>();
//            characteristics.add(Characteristics.IDENTITY_FINISH);
//            characteristics.add(Characteristics.UNORDERED);
//            characteristics.add(Characteristics.CONCURRENT);
//            return characteristics;
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT, Characteristics.UNORDERED));
        }
    }
    public static void main(String[] args) {
        // Collector interface
        // using my own toList
        List<Dish> menu = Dish.getMenu();

        List<Dish> vegetableDish = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(new ToListCollector<Dish>());
        System.out.println(vegetableDish);

        // performing a custom collect without creating a collector implementation
        // pros : short
        // cons : low readability, duplication when using many place, only IDENTITY_FINISH & CONCURRENT (not UNORDERED)
        List<Dish> vegetableDish2 = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(
                        ArrayList::new,
                        List::add,
                        List::addAll
                );
        System.out.println(vegetableDish2);

    }
}
