package Ch09_RefactorTestDebug;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class S4_Debugging {
    public static void main(String[] args) {
        s941_checkStackTrace();
        s944_usingPeekOfStream();
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

    private static void s944_usingPeekOfStream() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        numbers.stream()
                .map(x -> x + 17)
                .filter(x -> x % 2 == 0)
                .limit(3)
                .forEach(System.out::println);

        // using peek() to check without consume the stream
        List<Integer> result = numbers.stream()
                .peek(x -> System.out.println("from stream : " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map : " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter : " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit : " + x))
                .collect(Collectors.toList());
        System.out.println(result);
    }

    public static int divideByZero(int n) {
        return n / 0;
    }
}
