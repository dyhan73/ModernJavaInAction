package Ch09_RefactorTestDebug;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class S24_ChainOfResponsibility {
    static abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    static class HeaderTextProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return "From Daeyoung : " + input;
        }
    }

    static class SpellCheckerProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return input.replaceAll("lamda", "lambda");
        }
    }

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result = p1.handle("Arn't lamda really sexy?");
        System.out.println(result);

        // lambda way - using unaryOperator
        UnaryOperator<String> headerProcessing = text -> "From DY : " + text;
        UnaryOperator<String> spellingCheckerProcessing = text -> text.replaceAll("lamda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellingCheckerProcessing);
        result = pipeline.apply("Lambda - Aren't lamda really sexy?");
        System.out.println(result);
    }
}
