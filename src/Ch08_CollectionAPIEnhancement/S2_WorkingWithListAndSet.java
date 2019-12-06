package Ch08_CollectionAPIEnhancement;

import java.util.*;
import java.util.stream.Collectors;

public class S2_WorkingWithListAndSet {
    static class Transaction {
        private int id;
        private String referenceCode;

        public Transaction(int id, String referenceCode) {
            this.id = id;
            this.referenceCode = referenceCode;
        }

        public String getReferenceCode() {
            return referenceCode;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "id=" + id +
                    ", referenceCode='" + referenceCode + '\'' +
                    '}';
        }

        public static List<Transaction> getSamples() {
            List<Transaction> samples = new ArrayList<>();
            samples.add(new Transaction(1, "abc"));
            samples.add(new Transaction(2, "def"));
            samples.add(new Transaction(3, "111"));
            samples.add(new Transaction(4, "5abc"));
            samples.add(new Transaction(5, "ZZZ"));
            return samples;
        }
    }
    public static void main(String[] args) {
        System.out.println("=========== Section 8.2.1");
        // remove implements
        List<Transaction> transactions = Transaction.getSamples();
        System.out.println(transactions);
        try {
            // Occur ConcurrentModificationException due to modify List during iteration using Iterator
            for (Transaction transaction : transactions) {
                if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                    transactions.remove(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(transactions);

        // avoid exception version (separate iterator and use iterator.remove
        transactions = Transaction.getSamples();
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = iterator.next();
            if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                iterator.remove();
            }
        }
        System.out.println(transactions);

        // removeIf() - same as auto improved version by IntelliJ
        transactions = Transaction.getSamples();
        System.out.println(transactions.hashCode());
        System.out.println(transactions.hashCode());
        transactions.removeIf(transaction -> Character.isDigit(transaction.getReferenceCode().charAt(0)));
        System.out.println(transactions.hashCode());
        System.out.println(transactions);

        System.out.println("=========== Section 8.2.2");
        transactions = Transaction.getSamples();
        List<String> referenceCodes = transactions.stream().map(Transaction::getReferenceCode).collect(Collectors.toList());
        System.out.println(referenceCodes);
        System.out.println(referenceCodes.hashCode());

        // replace element with stream (it makes new object, not replace element)
        List<String> upperFirstCodes = referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                .collect(Collectors.toList());
        System.out.println(upperFirstCodes);
        System.out.println(referenceCodes.hashCode());

        // replace source element using ListIterator
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext();) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        System.out.println(referenceCodes);
        System.out.println(referenceCodes.hashCode()); // new List object

        // replaceAll()
        referenceCodes.replaceAll(code -> Character.toLowerCase(code.charAt(0)) + code.substring(1));
        System.out.println(referenceCodes);
        System.out.println(referenceCodes.hashCode());
    }
}
