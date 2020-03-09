package Ch15_ConceptsOfCompletableFutureAndReactiveProgramming;

import java.util.concurrent.*;
import java.util.function.IntConsumer;

public class S2_SyncAndAsyncAPIs {
    public static void main(String[] args) {
//        s15201_runThreadExample();
//        s15202_usingFuture();
//        s1521_usingFutureTypeApi();
//        s1522_reactiveTypeAPI();
//        s1523_avoidSleepingInThread();

    }

    private static void s1523_avoidSleepingInThread() {
        // avoid blocking (sleep) in thread
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        work1();
        scheduledExecutorService.schedule(S2_SyncAndAsyncAPIs::work2, 10, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();
    }

    private static void work1() {
        System.out.println("Hello from Work1!");
    }

    private static void work2() {
        System.out.println("Hello from Work2!");
    }

    private static void s1522_reactiveTypeAPI() {
        // reactive type API
        int x = 11;
        ThreadExample.Result result = new ThreadExample.Result();

        f3(x, (int y) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            result.left = y;
            System.out.println(result.left + ", " + result.right);
        });

        g3(x, (int y) -> {
            result.right = y;
            System.out.println(result.left + ", " + result.right);
        });
    }

    private static void s1521_usingFutureTypeApi() {
        // Future type API
        try {
            Future<Integer> y = f2(4);
            Future<Integer> z = g2(5);
            System.out.println(y.get() + ", " + z.get());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void s15202_usingFuture() {
        // it also occurs submit/get operations
        try {
            ExecutorServiceExample.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void s15201_runThreadExample() {
        // it occurs managing start/join
        try {
            ThreadExample.run();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static int f(int x) throws InterruptedException {
        Thread.sleep(1000);
        return x + 1;
    }

    public static int g(int x) throws InterruptedException {
        Thread.sleep(1500);
        return x + 1;
    }

    public static Future<Integer> f2(int x) throws InterruptedException {
        return new Future<Integer>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Integer get() throws InterruptedException, ExecutionException {
                return x + 333;
            }

            @Override
            public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return x + 333;
            }
        };
    }

    public static Future<Integer> g2(int x) throws InterruptedException {
        return new Future<Integer>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Integer get() throws InterruptedException, ExecutionException {
                return x + 666;
            }

            @Override
            public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return x + 666;
            }
        };
    }

    public static void f3(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(x + 222);
    }

    public static void g3(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(x + 322);
    }

    static class ThreadExample {
        public static void run() throws InterruptedException {
            int x = 1;
            Result result = new Result();

            Thread t1 = new Thread(() -> {
                try {
                    result.left = S2_SyncAndAsyncAPIs.f(x);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    result.right = S2_SyncAndAsyncAPIs.g(x);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(result.left + result.right);
        }

        private static class Result {
            private int left;
            private int right;
        }
    }

    private static class ExecutorServiceExample {
        public static void run() throws ExecutionException, InterruptedException {
            int x = 10;

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<Integer> y = executorService.submit(() -> f(x));
            Future<Integer> z = executorService.submit(() -> g(x));
            System.out.println(y.get() + z.get());

            executorService.shutdown();
        }
    }
}
