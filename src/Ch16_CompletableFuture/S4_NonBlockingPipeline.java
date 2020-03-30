package Ch16_CompletableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class S4_NonBlockingPipeline {
    public static class Discount {
        public enum Code {
            NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

            private final int percentage;

            Code(int percentage) {
                this.percentage = percentage;
            }
        }

        public static String applyDiscount(Quote quote) {
            return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
        }

        private static double apply(double price, Code code) {
            S2_AsyncAPI.delay(1000);
            return price * (100 - code.percentage) / 100;
        }
    }

    public static class ShopS4 extends S2_AsyncAPI.Shop {

        public ShopS4(String name) {
            super(name);
        }

        public String getPriceS4(String product) {
            double price = calculatePrice(product);
            Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
            return String.format("%s:%.2f:%s", name, price, code);
        }
    }

    public static class Quote {
        private final String shopName;
        private final double price;
        private final Discount.Code discountCode;

        public Quote(String shopName, double price, Discount.Code code) {
            this.shopName = shopName;
            this.price = price;
            this.discountCode = code;
        }

        public static Quote parse(String s) {
            String[] split = s.split(":");
            String shopName = split[0];
            Double price = Double.parseDouble(split[1]);
            Discount.Code discountCode = Discount.Code.valueOf(split[2]);
            return new Quote(shopName, price, discountCode);
        }

        public String getShopName() {
            return shopName;
        }

        public double getPrice() {
            return price;
        }

        public Discount.Code getDiscountCode() {
            return discountCode;
        }

        @Override
        public String toString() {
            return "Quote{" +
                    "shopName='" + shopName + '\'' +
                    ", price=" + price +
                    ", discountCode=" + discountCode +
                    '}';
        }
    }

    public static void main(String[] args) {
//        s1640_addCodeOnShop();
//        s1641_addDiscount();
//        s1642_discountServiceWithStream();
        s1643_discountServiceWithCompletableFuture();
        s1643_discountServiceWithCompletableFutureNoExecutor();
    }

    private static void s1640_addCodeOnShop() {
        ShopS4 shop = new ShopS4("BestPrice");
        System.out.println(shop.getPriceS4("Samsung S20"));

    }

    private static void s1641_addDiscount() {
        ShopS4 shop = new ShopS4("BestPrice");
        String priceInfo = shop.getPriceS4("Samsung S20");
        System.out.println(priceInfo);
        System.out.println(Quote.parse(priceInfo));
        System.out.println(Discount.applyDiscount(Quote.parse(priceInfo)));
    }

    public static List<String> findPrices(List<ShopS4> shops, String product) {
        return shops.stream()
                .map(shop -> shop.getPriceS4(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    private static void s1642_discountServiceWithStream() {
        long start = System.nanoTime();
        List<ShopS4> shops = Arrays.asList(
                new ShopS4("BestPrice"),
                new ShopS4("LetsSaveBig"),
                new ShopS4("MyFavoriteShop"),
                new ShopS4("MyFavoriteShop2"),
                new ShopS4("MyFavoriteShop3"),
                new ShopS4("MyFavoriteShop4"),
                new ShopS4("MyFavoriteShop5"),
                new ShopS4("BuyItAll"));

        System.out.println(findPrices(shops, "Samsung S20"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPricesWithCompletableFuture(List<ShopS4> shops, String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        return thread;
                    }
                });

        List<CompletableFuture<String>> priceFutures =
            shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPriceS4(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private static void s1643_discountServiceWithCompletableFuture() {
        long start = System.nanoTime();
        List<ShopS4> shops = Arrays.asList(
                new ShopS4("BestPrice"),
                new ShopS4("LetsSaveBig"),
                new ShopS4("MyFavoriteShop"),
                new ShopS4("MyFavoriteShop2"),
                new ShopS4("MyFavoriteShop3"),
                new ShopS4("MyFavoriteShop4"),
                new ShopS4("MyFavoriteShop5"),
                new ShopS4("BuyItAll"));

        System.out.println(findPricesWithCompletableFuture(shops, "Samsung S20"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPricesWithCompletableFutureNoExecutor(List<ShopS4> shops, String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPriceS4(product)))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private static void s1643_discountServiceWithCompletableFutureNoExecutor() {
        long start = System.nanoTime();
        List<ShopS4> shops = Arrays.asList(
                new ShopS4("BestPrice"),
                new ShopS4("LetsSaveBig"),
                new ShopS4("MyFavoriteShop"),
                new ShopS4("MyFavoriteShop2"),
                new ShopS4("MyFavoriteShop3"),
                new ShopS4("MyFavoriteShop4"),
                new ShopS4("MyFavoriteShop5"),
                new ShopS4("BuyItAll"));

        System.out.println(findPricesWithCompletableFutureNoExecutor(shops, "Samsung S20"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }


}
