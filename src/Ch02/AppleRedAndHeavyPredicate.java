package Ch02;

public class AppleRedAndHeavyPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150
                && apple.getColor().equals(AppleColor.RED);
    }
}
