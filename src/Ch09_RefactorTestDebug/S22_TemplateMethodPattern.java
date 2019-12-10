package Ch09_RefactorTestDebug;

import java.util.Map;
import java.util.function.Consumer;

/** Template Method Pattern
 *  is needed when you offer concept of algorithm with possibility of modification the algorithm
 *  (Offer template)
 */
public class S22_TemplateMethodPattern {
    static class Customer {
        private int id;
        private String name;

        public Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static class Database {
        private static Map<Integer, String> db = Map.of(1, "Daeyoung", 2, "sealover");

        static Customer getCustomerWithId(int id) {
            String name = db.get(id);
            if (name != null) return new Customer(id, name);
            return null;
        }
    }


    abstract static class OnlineBanking {
        public void processCustomer(int id) {
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }

        abstract void makeCustomerHappy(Customer c);
    }

    static class MyBanking extends OnlineBanking {
        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println(c + " is happy with MyBanking");
        }
    }

    static class OnlineBankingLambda {
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }

        void greetingCustomer(int id, Consumer<Customer> greetingCustomer) {
            Customer c = Database.getCustomerWithId(id);
            greetingCustomer.accept(c);
        }
    }

    public static void main(String[] args) {
        // traditional way with extend sub class (MyBanking)
        OnlineBanking banking = new MyBanking();
        banking.processCustomer(1);

        // Lambda way
        OnlineBankingLambda bank = new OnlineBankingLambda();
        bank.processCustomer(2, (Customer c) -> System.out.println(c + " is happy with Lambda"));
        bank.greetingCustomer(1, c -> System.out.println("Hello " + c.name));

    }
}
