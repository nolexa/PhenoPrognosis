//Title:        Pheno-Prognosis
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn.entity;

public class ClimateZone {

/*
String name;
int startDecade = 12;
double[] temp = { 3.5,  6.5,  9.5, 11.6, 13.9, 15.5, 16.8, 18.4, 18.8,
                 18.2, 16.5, 15.4, 13.0, 11.0,  9.7,  6.9,  3.2};
*/

/*  DecadeTemperature[] decades = new DecadeTemperature[] {
    new DecadeTemperature(12, 3.5),
    new DecadeTemperature(13, 6.5),
    new DecadeTemperature(14, 9.5),
    new DecadeTemperature(15, 11.6),
    new DecadeTemperature(16, 13.9),
    new DecadeTemperature(17, 15.5),
    new DecadeTemperature(18, 16.8),
    new DecadeTemperature(19, 18.4),
    new DecadeTemperature(20, 18.8),
    new DecadeTemperature(21, 18.2),
    new DecadeTemperature(22, 16.5),
    new DecadeTemperature(23, 15.4),
    new DecadeTemperature(24, 13.0),
    new DecadeTemperature(25, 11.0),
    new DecadeTemperature(26, 9.7),
    new DecadeTemperature(27, 6.9),
    new DecadeTemperature(28, 3.2)
  };
*/

    private String name;
    private int startDecade;
    private double[] decadeAvgTemperatures;
    private int frostDay;

    public ClimateZone(String name, int startDecade, double[] decadeAvgTemperatures, int frostDay) {
        this.name = name;
        this.startDecade = startDecade;
        this.decadeAvgTemperatures = decadeAvgTemperatures;
        this.frostDay = frostDay;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(String.format("%s %d\n", name, startDecade));
        for (double aTemp : decadeAvgTemperatures) {
            s.append(aTemp).append("\n");
        }
        return s.toString();
    }

    public String getName() {
        return name;
    }

    public int getStartDecade() {
        return startDecade;
    }

    public double[] getDecadeAvgTemperatures() {
        return decadeAvgTemperatures;
    }

    public int getFrostDay() {
        return frostDay;
    }
}