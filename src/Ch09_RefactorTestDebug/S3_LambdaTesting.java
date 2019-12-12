package Ch09_RefactorTestDebug;

import java.util.Comparator;

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
        public final static Comparator<Point> compareByXAndThenY = Comparator.comparing(Point::getX).thenComparing(Point::getY);
    }
}
