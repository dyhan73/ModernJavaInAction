package Ch10_DSLWithLambda.S3_JavaDSLPattern;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class S34_MixedPattern {
    static class MixedBuilder {
        static Order forCustomer(String customer, TradeBuilder... builders) {
            Order order = new Order();
            order.setCustomer(customer);
            Stream.of(builders).forEach(b -> order.addTrade(b.trade));
            return order;
        }

        static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
            return buildTrade(consumer, Trade.Type.BUY);
        }

        static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
            return buildTrade(consumer, Trade.Type.SELL);
        }

        static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type buy) {
            TradeBuilder builder = new TradeBuilder();
            builder.trade.setType(buy);
            consumer.accept(builder);
            return builder;
        }
    }

    static class TradeBuilder {
        private Trade trade = new Trade();

        TradeBuilder quantity(int quantity) {
            trade.setQuantity(quantity);
            return this;
        }

        TradeBuilder at(double price) {
            trade.setPrice(price);
            return this;
        }

        StockBuilder stock(String symbol) {
            return new StockBuilder(this, trade, symbol);
        }
    }

    static class StockBuilder {
        private final TradeBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        public TradeBuilder on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return builder;
        }
    }

    public static void main(String[] args) {
        Order order = S34_MixedPattern.MixedBuilder.forCustomer(
                "BigBank",
                MixedBuilder.buy(t -> t.quantity(80).stock("IBM").on("NYSE").at(125.00)),
                MixedBuilder.sell(t -> t.quantity(50).stock("GOOGLE").on("NASDAQ").at(125.00))
        );
        System.out.println(order);
    }
}
