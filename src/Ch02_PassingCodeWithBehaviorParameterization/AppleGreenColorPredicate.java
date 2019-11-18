package Ch02_PassingCodeWithBehaviorParameterization;

import static Ch02_PassingCodeWithBehaviorParameterization.AppleColor.GREEN;

public class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}
