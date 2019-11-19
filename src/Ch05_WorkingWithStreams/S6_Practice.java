package Ch05_WorkingWithStreams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class S6_Practice {
    static class Trader {
        private final String name;
        private final String city;

        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader : " + name + " in " + city;
        }
    }

    static class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Transaction {" + trader + ", year=" + year + ", value=" + value + "}";
        }
    }

    public static void main(String[] args) {
        // initial
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        q1(transactions);
        q2(transactions);
        q3(transactions);
        q4(transactions);
        q5(transactions);
        q6(transactions);
        q7(transactions);
        q8(transactions);
    }

    private static void q1(List<Transaction> transactions) {
        // Q1 - Find all transactions in the year 2011 and sort them by value (small to high)
        List<Transaction> q1Result = transactions.stream()
                .filter(transaction -> transaction.year == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(q1Result);
    }

    private static void q2(List<Transaction> transactions) {
        // What are all the unique cities where the traders work?
        List<String> q1Result = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(q1Result);
    }

    private static void q3(List<Transaction> transactions) {
        // Find all traders from Cambridge and sort them by name
        List<Trader> q3Result = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader())
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(q3Result);
    }

    private static void q4(List<Transaction> transactions) {
        // Return a string of all traders’ names sorted alphabetically
        String q4Result = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println(q4Result);
    }

    private static void q5(List<Transaction> transactions) {
        // Are any traders based in Milan?
        boolean q5Result = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(q5Result);
    }

    private static void q6(List<Transaction> transactions) {
        // Print the values of all transactions from the traders living in Cambridge
        List<Integer> q6Result = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println(q6Result);
        // or
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    private static void q7(List<Transaction> transactions) {
        // What’s the highest value of all the transactions?
        transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo)
                .ifPresent(System.out::println);
        // or
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
        // or
        transactions.stream()
                .reduce((t1, t2) -> t1.getValue() > t2.getValue() ? t1 : t2)
                .map(Transaction::getValue)
                .ifPresent(System.out::println);
    }

    private static void q8(List<Transaction> transactions) {
        //Find the transaction with the smallest value
        transactions.stream()
                .map(Transaction::getValue)
                .min(Integer::compareTo)
                .ifPresent(System.out::println);
    }
}
