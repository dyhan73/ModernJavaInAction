package Ch16_CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class S2_AsyncAPI {
    static class Shop {
        String name;
        Random random = new Random();

        public Shop(String name) {
            this.name = name;
        }

        public double getPrice(String product) {
            return calculatePrice(product);
        }

        private double calculatePrice(String product) {
            if (product == "emptyProduct")
                throw new RuntimeException("Product not available");

            delay(1500);
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }

        public Future<Double> getPriceAsync(String product) {
            CompletableFuture<Double> futurePrice = new CompletableFuture<>();
            new Thread( () -> {
                try {
                    double price = calculatePrice(product);
                    futurePrice.complete(price);
                } catch (Exception e) {
                    futurePrice.completeExceptionally(e);
                }
            }).start();
            return futurePrice;
        }

        public Future<Double> getPriceAsync2(String product) {
            return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        }
    }

    public static void delay(int mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");

        procMain(shop, "my favorite product");
        procMain(shop, "emptyProduct");
    }

    private static void procMain(Shop shop, String product) {
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync2(product);
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        doSomethingElse();

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f\n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void doSomethingElse() {
        delay(1000);
    }
}
