package Ch09_RefactorTestDebug;

import java.util.*;
import java.util.stream.Collectors;

public class S3_LambdaTesting {
    static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point moveRightBy(int x) {
            return new Point(this.x + x, this.y);
        }

        // static variable of lambda
        public final static Comparator<Point> compareByXAndThenY =
                Comparator.comparing(Point::getX).thenComparing(Point::getY);

        // wrapping lambda as (static) method
        public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
            return points.stream()
                    .map(p -> new Point(p.getX() + x, p.getY()))
                    .collect(Collectors.toList());
        }

        // equals must override to avoid using Object's default equals()
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
