package Ch06_CollectingDataWithStreams;

import java.util.*;
import java.util.stream.Collectors;

public class S0_Intro {
    static class Transaction {
        private String currency;
        private int year;
        private int amount;

        Transaction(String currency, int year, int amount) {
            this.currency = currency;
            this.year = year;
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public int getYear() {
            return year;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "currency=" + currency +
                    ", year=" + year +
                    ", amount=" + amount +
                    '}';
        }

        static List<Transaction> initTransactions() {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(new Transaction("WON", 2019, 1000));
            transactions.add(new Transaction("WON", 2019, 2000));
            transactions.add(new Transaction("WON", 2019, 3000));
            transactions.add(new Transaction("USD", 2019, 3));
            transactions.add(new Transaction("USD", 2019, 5));
            transactions.add(new Transaction("USD", 2019, 6));
            return transactions;
        }
    }

    public static void main(String[] args) {
        s60_CompareWithImperative();
    }

    private static void s60_CompareWithImperative() {
        // Grouping by Currency of transaction
        // Imperative version
        List<S0_Intro.Transaction> transactions = S0_Intro.Transaction.initTransactions();
        Map<String, List<S0_Intro.Transaction>> transactionByCurrencies = new HashMap<>();

        for (S0_Intro.Transaction transaction : transactions) {
            String currency = transaction.getCurrency();
            List<S0_Intro.Transaction> transactionForCurrency = transactionByCurrencies.get(currency);
            if (transactionForCurrency == null) {
                transactionForCurrency = new ArrayList<>();
                transactionByCurrencies.put(currency, transactionForCurrency);
            }
            transactionForCurrency.add(transaction);
        }
        transactionByCurrencies.forEach((k, v) -> System.out.println(k + " : " + v));

        // Stream version
        transactionByCurrencies = transactions.stream()
                .collect(Collectors.groupingBy(S0_Intro.Transaction::getCurrency));
        transactionByCurrencies.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
