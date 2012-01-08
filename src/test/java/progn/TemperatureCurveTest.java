package progn;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created: 1/8/12 11:15 PM
 *
 * @author Alexei Novikov <alexei.novikov@lequa.com>
 */
public class TemperatureCurveTest {

    @Test
    public void testConstruct() {
        int startDecade = 2;
        int startFull = 0;
        double[] decadeAvgTemperatures = new double[]{-1, -1, 1, 2, 3, 4, 5};
        TemperatureCurve tc = new TemperatureCurve(startDecade, startFull, decadeAvgTemperatures);
        double[] points = new double[]{1, 1.5, 2, 2.5, 3.0};
        Assert.assertArrayEquals(points, tc.getPoints(), 0.00001);
    }
}
