package malekire.devilrycraft.util.math.beziercurves;
import org.junit.Test;

import static org.junit.Assert.*;


public class BezierCurveTest {

    @Test
    public void testCurves() {
        BezierCurve curve1 = new BezierCurve();
        curve1.addPoint(new Point(0, 0));
        curve1.addPoint(new Point(0, 0));
        curve1.addPoint(new Point(0, 0));
        curve1.addPoint(new Point(0, 0));
        assertEquals(curve1.getX(0.5), 0, 0);
    }
}
