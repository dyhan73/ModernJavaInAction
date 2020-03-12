package Ch15_ConceptsOfCompletableFutureAndReactiveProgramming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class S4_CompletableFuture {
    public static void main(String[] args) {
        s1540_simpleUsage();
        s1541_thenCombine();
    }

    private static void s1541_thenCombine() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        // no waiting (without get())
        CompletableFuture<String> c = a.thenCombine(b, (y, z) -> y + ", " + z);

        executorService.submit(() -> a.complete(f(x)));
        executorService.submit(() -> b.complete(g(x)));

        try {
            System.out.println(c.get());
        } catch (Exception e) {
            System.out.println(e);
        }

        executorService.shutdown();
    }

    private static void s1540_simpleUsage() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(f(x)));
        int b = g(x);
        try {
            System.out.println("before get()");
            System.out.println(a.get() + ", " + b);  // waiting get() until return
        } catch (Exception e) {
            System.out.println(e);;
        }

        executorService.shutdown();
    }

    private static int f(int x) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        return x+1;
    }

    private static int g(int x) {
        return x+2;
    }

}
