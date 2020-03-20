package Ch16_CompletableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import static Ch16_CompletableFuture.S2_AsyncAPI.*;

/**
 * making nonblocking code with BLOCKING API
 */
public class S3_NonblockingCode {

    private List<Shop> shops;
    private final Executor executor;

    public S3_NonblockingCode() {
        this.shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop2"),
                new Shop("MyFavoriteShop3"),
                new Shop("MyFavoriteShop4"),
                new Shop("MyFavoriteShop5"),
                new Shop("BuyItAll"));
        executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        return thread;
                    }
                });
    }

    public List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithCompletableFutureAndCustomExecutor(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        S3_NonblockingCode cls = new S3_NonblockingCode();

        s1630_findWithStream(cls);
        s1631_findWithParallelStream(cls);
        s1632_findWithCompletableFuture(cls);

        System.out.println(Runtime.getRuntime().availableProcessors());

        s1633_findWithCompletableFutureAndCustomExecutor(cls);
    }

    private static void s1630_findWithStream(S3_NonblockingCode cls) {
        long start = System.nanoTime();
        List<String> priceList = cls.findPrices("toy");
        System.out.println(priceList);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static void s1631_findWithParallelStream(S3_NonblockingCode cls) {
        long start = System.nanoTime();
        List<String> priceList = cls.findPricesParallel("toy");
        System.out.println(priceList);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static void s1632_findWithCompletableFuture(S3_NonblockingCode cls) {
        long start = System.nanoTime();
        List<String> priceList = cls.findPricesWithCompletableFuture("toy");
        System.out.println(priceList);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static void s1633_findWithCompletableFutureAndCustomExecutor(S3_NonblockingCode cls) {
        long start = System.nanoTime();
        List<String> priceList = cls.findPricesWithCompletableFutureAndCustomExecutor("toy");
        System.out.println(priceList);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}
