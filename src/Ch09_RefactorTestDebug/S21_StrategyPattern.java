package Ch09_RefactorTestDebug;

public class S21_StrategyPattern {
    public interface ValidationStrategy {
        boolean execute(String s);
    }

    static class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    static class IsNureric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    static class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

    public static void main(String[] args) {
        // traditional implementation of Strategy Pattern
        Validator numericValidator = new Validator(new IsNureric());
        boolean b1 = numericValidator.validate("aaaa");
        System.out.println(b1);

        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbbb");
        System.out.println(b2);

        // lambda way
        Validator numericValidatorLambda = new Validator(s -> s.matches("[a-z]+"));
        boolean b3 = numericValidatorLambda.validate("aaaaa");
        System.out.println(b3);
        Validator lowerCaseValidatorLambda = new Validator(s -> s.matches("\\d+"));
        boolean b4 = lowerCaseValidatorLambda.validate("bbbbb");
        System.out.println(b4);
    }
}
