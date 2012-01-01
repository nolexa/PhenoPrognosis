package progn.gui;

//import javax.swing.UIManager;
//import javax.swing.WindowConstants;

import progn.TemperatureCurve;
import progn.YearCalendar;
import progn.entity.ClimateZone;
import progn.entity.Sort;
import progn.gui.PrognoseFrame;
import progn.loaders.ClimateLoader;
import progn.loaders.SortLoader;

import java.net.URL;

public class PhenoPrognosis {
    Sort sort;
    ClimateZone zone;
    Sort[] sorts;
    ClimateZone[] zones;
    int nextPhaseDay = 122;
    int startDecade;
    int increment;
    TemperatureCurve curve;
    PrognoseFrame frame;
    boolean interrupted = false;
    int startPer = 0;

    // Construct the application
    public PhenoPrognosis(URL base) {
        /*
           * PhenoPhase[] lifeCycle = new PhenoPhase[] { new
           * PhenoPhase(4.3,-0.82), new PhenoPhase(5.6,-0.69), new
           * PhenoPhase(8.3,-1.62) };
           */
        // sort = new Sort("","","",lifeCycle);
        SortLoader sl = new SortLoader("sorts.txt", base);
        ClimateLoader cl = new ClimateLoader("zones.txt", base);
        String[] sn = new String[sl.getSorts().size()];
        String[] zn = new String[cl.getZones().size()];
        sorts = new Sort[sl.getSorts().size()];
        zones = new ClimateZone[cl.getZones().size()];
        for (int x = 0; x < sl.getSorts().size(); x++) {
            Sort s = sl.getSorts().get(x);
            sorts[x] = s;
            sn[x] = s.getCulture() + " " + s.getType() + " " + s.getPrecocity();
        }
        for (int x = 0; x < cl.getZones().size(); x++) {
            ClimateZone s = cl.getZones().get(x);
            zones[x] = s;
            zn[x] = s.getName();
            //System.out.println(zn[x]);
        }
        frame = new PrognoseFrame(this, sn, zn);
    }

    public void startPrognoze(int s, int z, int start) {
        sort = sorts[s];
        zone = zones[z];
        // System.out.println("Z: "+zone.temp[0]);
        nextPhaseDay = start;
        // System.out.println(start);
        prognoze();
    }

    public void prognoze() {
        for (int lifePhase = startPer; lifePhase < sort.getLifeCycle().length; lifePhase++) {
            int xx[] = YearCalendar.dayToDecadeSpec(nextPhaseDay);
            int xx2[] = YearCalendar.dayToDecade(nextPhaseDay);
            /*
                * System.out.println("x-s"); System.out.println(xx[0]);
                * System.out.println(xx[1]); System.out.println(xx2[0]);
                * System.out.println(xx2[1]); System.out.println("e-s");
                */
            startDecade = xx2[0];
            increment = xx2[1];
            try {
                if (xx[0] != 0) {
                    curve = TemperatureCurve.build(xx[0], zone.getStartDecade(),
                            zone.getDecadeAvgTemperatures(), xx[1]);
                } else {
                    curve = new TemperatureCurve(xx[0], zone.getStartDecade(),
                            zone.getDecadeAvgTemperatures());
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // e.printStackTrace();
                frame.area.append("Температурные данные на данный период отсутствуют");
                // interrupted = true;
                return;
            }
            //Add info on currect life phase
            addInfo(lifePhase);
            //Calculate next life phase
            try {
                compare(lifePhase);
            } catch (ArrayIndexOutOfBoundsException e) {
                // e.printStackTrace();
                frame.area.append("Не созреет");
                // interrupted = true;
                return;
            }
        }
        if (interrupted) {
            interrupted = false;
        } else {
            addInfo(sort.getLifeCycle().length);
        }
    }

    void addInfo(int cycle) {
        if (interrupted)
            return;
        frame.area.append(Sort.ALLNAMES[sort.getNames()[cycle]] + ": ");
        int[] nextPhaseDate = YearCalendar.dayToDate(nextPhaseDay);
        String month = nextPhaseDate[0] + "";
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = nextPhaseDate[1] + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        frame.area.append(day + "." + month + "\n");
        if (cycle == sort.getLifeCycle().length) {
            if (nextPhaseDay >= zone.getFrostDay()) {
                System.out.println("Warn");
                int[] frostDate = YearCalendar.dayToDate(zone.getFrostDay());
                month = frostDate[0] + "";
                if (month.length() == 1) {
                    month = "0" + month;
                }
                day = frostDate[1] + "";
                if (day.length() == 1) {
                    day = "0" + day;
                }
                frame.area.append("\nПредупреждение: " + day + "." + month
                        + " вероятность первого заморозка в воздухе (2 м) 20%");
            }
        }
    }

    void compare(int cycle) throws ArrayIndexOutOfBoundsException {
        double factor = Math.pow(Math.E, sort.getLifeCycle()[cycle].getFactor());
        // System.out.println(factor);
        double exp = sort.getLifeCycle()[cycle].getExponent();
        double result2 = Math.pow(curve.getPoints()[0], exp) * factor;
        double result1;
        int com1;
        int com2;
        int x = 0;
        int decade = startDecade;
        do {
            x++;
            decade++;
            result1 = result2;
            result2 = Math.pow(curve.getPoints()[x], exp) * factor;
            // System.out.println("E="+Math.E);
            /*
                * System.out.println(result1);
                * System.out.println(YearCalendar.decadeToDay(decade,startDecade));
                * System.out.println(result2);
                * System.out.println(YearCalendar.decadeToDay(decade +
                * 1,startDecade)); System.out.println("next\n");
                */
            com1 = YearCalendar.decadeToDay(decade, startDecade + 1);
            if ((x == (curve.getPoints().length - 1)) && (increment != 0)) {
                com2 = com1 + increment;
            } else {
                com2 = YearCalendar.decadeToDay(decade + 1, startDecade + 1);
            }
            /*
                * System.out.println("result1: "+result1);
                * System.out.println("result2: "+result2);
                * System.out.println("com1: "+com1); System.out.println("com2:
                * "+com2); System.out.println();
                */
        } while ((result1 > com1) == (result2 > com2));
        // System.out.println("end\n");
        int day = YearCalendar.decadeToDay(decade, startDecade + 1);
        int pieces = YearCalendar.decades[decade] + 2;
        double[] p = new double[pieces];
        double from = curve.getPoints()[x - 1];
        double to = curve.getPoints()[x];
        // System.out.println("from: " + from);
        // System.out.println("to: " + to);
        // double from = com1;
        // double to = com2;
        // System.out.println("pieces:");
        for (int z = 0; z < pieces; z++) {
            // p[z] = from + (to - from) * ((double)(z) / (double)(pieces));
            p[z] = from + (to - from) * ((double) (z) / (double) (pieces));
            // System.out.println(p[z]);
        }
        // System.out.println("end pieces");
        result2 = result1;
        x = 0;
        do {
            x++;
            result1 = result2;
            result2 = Math.pow(p[x], exp) * factor;
        } while ((result1 > (day + x - 1)) == (result2 > (day + x)));
        double[] m = {result1, result2};
        nextPhaseDay += ((int) (TemperatureCurve.countMiddle(m, 0, 2)));
    }

    public void endWork() {
        curve = null;
        frame = null;
    }
}