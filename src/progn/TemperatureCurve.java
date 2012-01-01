//Title:        Pheno-Prognosis
//Version:
//Copyright:    Copyright (c) 2000
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn;

public class TemperatureCurve {
    private int startDecade;    // The decade where the curve started
    private double[] points;    // Average temperatures
    private int increment;

    public TemperatureCurve(int startDecade, int startFull, double[] decadeAvgTemperatures) throws ArrayIndexOutOfBoundsException {
        int offset = startDecade - startFull;
        double[] decades = new double[decadeAvgTemperatures.length - offset];
        System.arraycopy(decadeAvgTemperatures, offset, decades, 0, decades.length);
        this.startDecade = startDecade;
//    int x = find(decades,startDecade);
        points = new double[decades.length];
        for (int z = 0; z < points.length; z++) {
            points[z] = countMiddle(decades, 0, z + 1);
//      System.out.println(points[z]);
        }
        increment = 0;
//    System.out.println("next\n");
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

    /*
      int find(DecadeTemperature[] decades,int startDecade)  throws ArrayIndexOutOfBoundsException {
        boolean find = false;
        int x = 0;
        for(;(!find);x++){
           if(decades[x].decadeNum == startDecade){
             find = true;
           }
        }
         return --x;
      }
    */
    public static double countMiddle(double[] source, int start, int stop) throws ArrayIndexOutOfBoundsException {
        double sum = 0;
        for (int x = start; x < stop; x++) {
            sum += source[x];
        }
        return (sum / (double) (stop - start));
    }

    public double[] getPoints() {
        return points;
    }

}