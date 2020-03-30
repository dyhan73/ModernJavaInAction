package Ch16_CompletableFuture;

import Ch16_CompletableFuture.S4_NonBlockingPipeline.Quote;
import Ch16_CompletableFuture.S4_NonBlockingPipeline.ShopS4;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

public class S5_ReactingToFutureCompletion {
    private static final Random random = new Random();

    /**
     * random delay (0.5~2.5 secs)
     */
    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    Executor executor;
    List<ShopS4> shops;

    public S5_ReactingToFutureCompletion() {
        shops = Arrays.asList(
                new ShopS5("BestPrice"),
                new ShopS5("LetsSaveBig"),
                new ShopS5("MyFavoriteShop"),
                new ShopS5("MyFavoriteShop2"),
                new ShopS5("MyFavoriteShop3"),
                new ShopS5("MyFavoriteShop4"),
                new ShopS5("MyFavoriteShop5"),
                new ShopS5("BuyItAll"));

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

    public static class ShopS5 extends ShopS4 {

        public ShopS5(String name) {
            super(name);
        }

        protected double calculatePrice(String product) {
            if (product == "emptyProduct")
                throw new RuntimeException("Product not available");

            randomDelay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }
    }


    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPriceS4(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                        () -> S4_NonBlockingPipeline.Discount.applyDiscount(quote), executor)
                ));
    }

    public static void main(String[] args) {
        S5_ReactingToFutureCompletion proc = new S5_ReactingToFutureCompletion();

        long start = System.nanoTime();
        CompletableFuture[] futures = proc.findPricesStream("My Phone")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) +" msecs)")))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
}
