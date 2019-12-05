package Ch07_ParallelDataProcessing;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.LongStream;

public class S2_ForkJoinFramework extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public S2_ForkJoinFramework(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public S2_ForkJoinFramework(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "S2_ForkJoinFramework{" +
                "numbers=" + numbers.length +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        S2_ForkJoinFramework leftTask = new S2_ForkJoinFramework(numbers, start, start + length/2);
        leftTask.fork();

        S2_ForkJoinFramework rightTask = new S2_ForkJoinFramework(numbers, start + length/2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();

        return leftResult + rightResult;
    }

    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new S2_ForkJoinFramework(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static void main(String[] args) {
        long sum = S2_ForkJoinFramework.forkJoinSum(100000);
        System.out.println(sum);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
