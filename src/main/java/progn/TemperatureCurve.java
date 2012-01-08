//Title:        Pheno-Prognosis
//Version:
//Copyright:    Copyright (c) 2000
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;

public class TemperatureCurve {
    private static final Log log = LogFactory.getLog(TemperatureCurve.class);

    private int startDecade;    // The decade where the curve started
    private double[] points;    // Average temperatures
    private int increment;

    public TemperatureCurve(int startDecade, int startFull, double[] decadeAvgTemperatures) throws ArrayIndexOutOfBoundsException {
        this.startDecade = startDecade;
        int offset = startDecade - startFull;
        points = new double[decadeAvgTemperatures.length - offset];
        for (int z = 0; z < points.length; z++) {
            points[z] = new DescriptiveStatistics(Arrays.copyOfRange(decadeAvgTemperatures, offset, offset + z + 1)).getMean();
        }
        increment = 0;
    }

    public static TemperatureCurve build(int startDecade, int startFull, double[] decades, int increment) throws ArrayIndexOutOfBoundsException {
/*    int offset = startDecade - startFull;
    double[] d1 = new double[decades.length - offset];
    double[] d2 = new double[decades.length - offset - 1];
    System.arraycopy(decades,offset,d1,0,decades.length - offset);
    System.arraycopy(decades,offset + 1,d2,0,decades.length - offset - 1);
*/
        TemperatureCurve c1 = new TemperatureCurve(startDecade, startFull, decades);
        TemperatureCurve c2 = new TemperatureCurve(startDecade + 1, startFull, decades);
        return new TemperatureCurve(c1, c2, increment);
    }

    protected TemperatureCurve(TemperatureCurve c1, TemperatureCurve c2, int increment) throws ArrayIndexOutOfBoundsException {
        double[] from = c1.points;
        double[] to = c2.points;
        this.increment = increment;
        this.startDecade = c1.startDecade;
        points = new double[from.length];
        double inc = ((double) (increment) / (double) (YearCalendar.decades[startDecade]));
        for (int x = 0; x < to.length; x++) {
            points[x] = from[x] + ((to[x] - from[x]) * inc);
            System.out.println(points[x]);
        }
        System.out.println("next\n");
        points[points.length - 1] = from[from.length - 1] + (to[to.length - 1] - from[from.length - 1]) * inc;
//    System.out.println(points[points.length - 1]);
    }

    public double[] getPoints() {
        return points;
    }

}