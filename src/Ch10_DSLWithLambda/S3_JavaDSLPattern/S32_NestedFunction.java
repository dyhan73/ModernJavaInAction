package Ch10_DSLWithLambda.S3_JavaDSLPattern;

import java.util.stream.Stream;

public class S32_NestedFunction {
    static class NestedFunctionOrderBuilder {
        static Order order(String customer, Trade... trades) {
            Order order = new Order();
            order.setCustomer(customer);
            Stream.of(trades).forEach(order::addTrade);
            return order;
        }

        static Trade buy(int quantity, Stock stock, double price) {
            return buildTrade(quantity, stock, price, Trade.Type.BUY);
        }

        static Trade sell(int quantity, Stock stock, double price) {
            return buildTrade(quantity, stock, price, Trade.Type.SELL);
        }

        private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type buy) {
            Trade trade = new Trade();
            trade.setQuantity(quantity);
            trade.setType(buy);
            trade.setStock(stock);
            trade.setPrice(price);
            return trade;
        }

        static double at(double price) {
            return price;
        }

        static Stock stock(String symbol, String market) {
            Stock stock = new Stock();
            stock.setSymbol(symbol);
            stock.setMarket(market);
            return stock;
        }

        static String on(String market) {
            return market;
        }
    }

    public static void main(String[] args) {
        // DSL - Nested function style
        Order order = NestedFunctionOrderBuilder.order("BigBank",
                NestedFunctionOrderBuilder.buy(80,
                        NestedFunctionOrderBuilder.stock("IBM", NestedFunctionOrderBuilder.on("NYSE")),
                        NestedFunctionOrderBuilder.at(125.00)),
                NestedFunctionOrderBuilder.sell(50,
                        NestedFunctionOrderBuilder.stock("GOOGLE", NestedFunctionOrderBuilder.on("NASDAQ")),
                        NestedFunctionOrderBuilder.at(375.00))
                );
        System.out.println(order);
    }
}
