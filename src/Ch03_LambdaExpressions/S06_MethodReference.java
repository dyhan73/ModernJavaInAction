package Ch03_LambdaExpressions;

import java.util.*;
import java.util.function.*;

public class S06_MethodReference {
    ToIntFunction<String> stringToInt;
    BiPredicate<List<String>, String> contains;
    Predicate<String> startsWithNumber;

    private boolean startsWithNumber(String s) {
        if (s.charAt(0) >= '0' && s.charAt(0) <= '9') return true;
        else return false;
    }

    void setWithLambda() {
        stringToInt = (String s) -> Integer.parseInt(s);
        contains = (list, element) -> list.contains(element);
        startsWithNumber = (String s) -> this.startsWithNumber(s);
    }

    public static void main(String[] args) {
        S06_MethodReference proc = new S06_MethodReference();
        proc.setWithLambda();

    }

}
