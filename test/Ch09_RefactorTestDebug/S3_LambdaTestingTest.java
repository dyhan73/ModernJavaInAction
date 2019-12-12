package Ch09_RefactorTestDebug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class S3_LambdaTestingTest {
    @Test
    public void testMoveRightBy() {
        S3_LambdaTesting.Point p1 = new S3_LambdaTesting.Point(5, 5);
        S3_LambdaTesting.Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }
}