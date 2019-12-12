package Ch09_RefactorTestDebug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class S3_LambdaTestingTest {
    @Test
    public void testMoveRightBy() {
        S3_LambdaTesting.Point p1 = new S3_LambdaTesting.Point(5, 5);
        S3_LambdaTesting.Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        S3_LambdaTesting.Point p1 = new S3_LambdaTesting.Point(10, 15);
        S3_LambdaTesting.Point p2 = new S3_LambdaTesting.Point(10, 20);
        int result = S3_LambdaTesting.Point.compareByXAndThenY.compare(p1, p2);
        assertTrue(result < 0);
    }

}