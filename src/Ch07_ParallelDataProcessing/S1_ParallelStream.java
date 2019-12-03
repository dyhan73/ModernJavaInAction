package Ch07_ParallelDataProcessing;

import java.util.stream.Stream;

public class S1_ParallelStream {
    public static void main(String[] args) {
        int limitCount = 10;

        // simple stream version
        long sum = Stream.iterate(1L, i -> i + 1)
                .limit(limitCount)
                .reduce(0L, Long::sum);
        System.out.println(sum);

        // traditional java loop
        long result = 0;
        for (long i = 1L; i <= limitCount; i++) {
            result += i;
        }
        System.out.println(result);

        // convert sequential to parallel
        sum = Stream.iterate(1L, i -> i + 1)
                .limit(limitCount)
                .parallel()
                .reduce(0L, Long::sum);
        System.out.println(sum);
    }
}
