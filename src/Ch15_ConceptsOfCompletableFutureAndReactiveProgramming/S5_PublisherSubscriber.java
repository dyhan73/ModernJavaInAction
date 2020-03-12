package Ch15_ConceptsOfCompletableFutureAndReactiveProgramming;

import java.util.ArrayList;
import java.util.List;

public class S5_PublisherSubscriber {
    public static void main(String[] args) {
        s1550_simpleCell();
        s1551_arithmaticCell();
        s1552_twoLevelPubSub();
    }

    private static void s1550_simpleCell() {
        System.out.println("----- " + new Object() {}.getClass().getEnclosingMethod().getName());
        SimpleCell c1 = new SimpleCell("C1");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c3 = new SimpleCell("C3");

        c1.subscribe(c3);

        c1.onNext(10);
        c2.onNext(20);
    }

    private static void s1551_arithmaticCell() {
        System.out.println("----- " + new Object() {}.getClass().getEnclosingMethod().getName());
        ArithmeticCell c3 = new ArithmeticCell("C3");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c1.onNext(10);
        c2.onNext(20);
        c1.onNext(15);
    }

    private static void s1552_twoLevelPubSub() {
        System.out.println("----- " + new Object() {}.getClass().getEnclosingMethod().getName());
        ArithmeticCell c5 = new ArithmeticCell("C5");
        ArithmeticCell c3 = new ArithmeticCell("C3");

        SimpleCell c1 = new SimpleCell("C1");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c4 = new SimpleCell("C4");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c3.subscribe(c5::setLeft);
        c4.subscribe(c5::setRight);

        c1.onNext(10);
        c2.onNext(20);
        c1.onNext(15);
        c4.onNext(1);
        c4.onNext(3);
    }

    private static class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {
        private int value = 0;
        private String name;
        private List<Subscriber> subscribers = new ArrayList<>();

        public SimpleCell(String name) {
            this.name = name;
        }

        @Override
        public void subscribe(Subscriber<? super Integer> subscriber) {
            subscribers.add(subscriber);
        }

        private void notifyAllSubscribers() {
            subscribers.forEach(subscriber -> subscriber.onNext(this.value));
        }

        @Override
        public void onNext(Integer newValue) {
            this.value = newValue;
            System.out.println(this.name + " : " + this.value);
            notifyAllSubscribers();
        }
    }

    interface Publisher<T> {
        void subscribe(Subscriber<? super T> subscriber);
    }

    interface Subscriber<T> {
        void onNext(T t);
    }

    private static class ArithmeticCell extends SimpleCell {
        private int left;
        private int right;

        public ArithmeticCell(String name) {
            super(name);
        }

        public void setLeft(int left) {
            this.left = left;
            onNext(left + this.right);
        }

        public void setRight(int right) {
            this.right = right;
            onNext(right + this.left);
        }
    }
}
