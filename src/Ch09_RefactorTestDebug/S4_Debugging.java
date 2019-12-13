package Ch09_RefactorTestDebug;

import java.util.Arrays;
import java.util.List;

public class S4_Debugging {
    public static void main(String[] args) {
        s941_checkStackTrace();
    }

    private static void s941_checkStackTrace() {
        // Occur NullPointerException
        List<S3_LambdaTesting.Point> points = Arrays.asList(new S3_LambdaTesting.Point(12, 2), null);
        try {
            // using lambda expression
            points.stream().map(p -> p.getX()).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // different stack trace (using method reference)
            points.stream().map(S3_LambdaTesting.Point::getX).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // can see the method name when method is defined same class
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        try {
            numbers.stream().map(S4_Debugging::divideByZero).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int divideByZero(int n) {
        return n / 0;
    }
}
