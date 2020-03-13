package Ch16_CompletableFuture;

import java.util.concurrent.*;

public class S1_SimpleUsage {
    public static void main(String[] args) {
        // example for before java8
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongCumputation();
            }
        });

        doSomethingElse();

        try {
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (TimeoutException e) {
            System.out.println(e);
        }
    }

    private static void doSomethingElse() {
        System.out.println("doSomethingElse");
    }

    private static Double doSomeLongCumputation() {
        System.out.println("doSomeLongComputation");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("finish long computation");
        return 88.88;
    }


}
