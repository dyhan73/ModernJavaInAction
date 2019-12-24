package Ch10_DSLWithLambda.S3_JavaDSLPattern;

import java.util.function.Consumer;

public class S33_FunctionSequencing {
    static class LambdaOrderBuilder {
        private Order order = new Order();

        static Order order(Consumer<LambdaOrderBuilder> consumer) {
            LambdaOrderBuilder builder = new LambdaOrderBuilder();
            consumer.accept(builder);
            return builder.order;
        }

        void forCustomer(String customer) {
            order.setCustomer(customer);
        }

        void buy(Consumer<TradeBuilder> consumer) {
            trade(consumer, Trade.Type.BUY);
        }

        void sell(Consumer<TradeBuilder> consumer) {
            trade(consumer, Trade.Type.SELL);
        }

        private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
            TradeBuilder builder = new TradeBuilder();
            builder.trade.setType(type);
            consumer.accept(builder);
            order.addTrade(builder.trade);
        }
    }

    static class TradeBuilder {
        private Trade trade = new Trade();

        void quantity(int quantity) {
            trade.setQuantity(quantity);
        }

        void price(double price) {
            trade.setPrice(price);
        }

        void stock(Consumer<StockBuilder> consumer) {
            StockBuilder builder = new StockBuilder();
            consumer.accept(builder);
            trade.setStock(builder.stock);
        }
    }

    static class StockBuilder {
        private Stock stock = new Stock();

        void symbol(String symbol) {
            stock.setSymbol(symbol);
        }

        void market(String market) {
            stock.setMarket(market);
        }
    }

    public static void main(String[] args) {
        Order order = LambdaOrderBuilder.order(o -> {
            o.forCustomer("BigBank");
            o.buy(t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock(s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell(t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock(s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });
        System.out.println(order);
    }
}
