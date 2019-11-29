package Ch06_CollectingDataWithStreams;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class S6_CustomCollectorForBetterPerformance {

    // improved version by less than square root
    private static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    // we can use isPrime3 if "primes" is managed (but basically NOT!)
    private static boolean isPrime3(List<Integer> primes, int candidate) {
        return primes.stream().noneMatch(i -> candidate % i == 0);
    }

    // more improvement with dividing by smaller prime than square root
    private static boolean isPrime4(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return primes.stream()
                .takeWhile(i -> i <= candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }


    // Quiz 6-3 : takeWhile method for java8
    private static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) return list.subList(0, i);
            i++;
        }
        return list;
    }

    // isPrime with our own takeWhile
    private static boolean isPrime5(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return takeWhile(primes, i -> i <= candidateRoot).stream()
                .noneMatch(i -> candidate % i == 0);
    }

    public static void main(String[] args) {
        s660_intro();
        s661_1_ImprovedIsPrime();
        s661_2_partitioningWithCustomCollector();

    }

    private static void s660_intro() {
        // get prime number (copy from 6.4)
        List<Integer> intList = Arrays.asList(11, 12, 13, 14, 15, 16, 17);
        Map<Boolean, List<Integer>> partByIsPrime = intList.stream().collect(
                partitioningBy(S6_CustomCollectorForBetterPerformance::isPrime)
        );
        System.out.println(partByIsPrime);

        // whole numbers in the range is needed to describe improving performance :)
        partByIsPrime = IntStream.rangeClosed(2, 100).boxed().collect(
                partitioningBy(S6_CustomCollectorForBetterPerformance::isPrime)
        );
        System.out.println(partByIsPrime);
    }

    private static void s661_1_ImprovedIsPrime() {
        // improve performance with custom collector
        // idea : divide by prime number only (using isPrime3)
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 9);
        System.out.println(isPrime3(primes, 11));
        System.out.println(isPrime4(primes, 11));
        System.out.println(isPrime5(primes, 11));
    }

    private static void s661_2_partitioningWithCustomCollector() {
        // using custom collector (PrimeNumbersCollector)
        Map<Boolean, List<Integer>> partitionPrime = partitionPrimeWithCustomCollector(100);
        System.out.println(partitionPrime);
    }

    private static class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>>
    {
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }};
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get( isPrime5(acc.get(true), candidate))
                        .add(candidate);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
//            throw new UnsupportedOperationException();
            // implement for just study. we have to get prime numbers sequentially
            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }

    private static Map<Boolean, List<Integer>> partitionPrimeWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumbersCollector());
    }
}
