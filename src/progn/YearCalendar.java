//Title:        Pheno-Prognosis
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn;

public class YearCalendar {
    public static int[] decades = new int[]{
            10, 10, 11,  //January
            10, 10, 9,   //February
            10, 10, 11,  //March
            10, 10, 10,  //April
            10, 10, 11,  //May
            10, 10, 10,  //June
            10, 10, 11,  //July
            10, 10, 11,  //August
            10, 10, 10,  //September
            10, 10, 11,  //October
            10, 10, 10,  //November
            10, 10, 11,  //December
    };

    public YearCalendar() {
    }

    /**
     * Converts a decade number into the number of this decade first day
     */
    public static int decadeToDay(int decade, int start) {
        int day = 1;
        for (int i = start; i < decade; i++)
            day += decades[i];
        return day;
    }

    public static int[] dayToDecade(int day) {
        int[] ret = new int[2];
        int decade = 1;
        while (day > decades[decade - 1]) {
            day -= decades[decade - 1];
            decade++;
        }
        ret[0] = decade;
        ret[1] = day;
        return ret;
    }

    public static int[] dayToDecadeSpec(int day) {
        day -= 4;
        int[] ret = new int[2];
        int decade = 1;
        while (day > decades[decade - 1]) {
            day -= decades[decade - 1];
            decade++;
        }
        ret[0] = decade;
        ret[1] = day;
/*
    if(decades[decade- 1] == 11){
      ret[1] -=1;
    }
*/
        return ret;
    }

    public static int decadeToDaySpec(int decade, int start) {
        int day = 4;
        for (int i = start + 1; i < decade; i++)
            day += decades[i];
        return day;
    }

    public static int dateToDay(int day, int month) {
//    System.out.println(day);
//    System.out.println(month);
        int decade = (month - 1) * 3;
        while (day > decades[decade]) {
            day -= decades[decade];
            decade++;
        }
        int d = decadeToDay(decade, 0) + day - 1;
//    System.out.println("Exit: "+d);
        return d;
    }

    public static int[] dayToDate(int day) {
//    System.out.println("Enter: "+day);
//    System.out.println(day);
        int[] ret = new int[2];
        int[] x = dayToDecade(day);
//    System.out.println(x[0]);
//    System.out.println(x[1]);
        x[0]--;
        ret[0] = 1;
        while (x[0] > 2) {
            x[0] -= 3;
            ret[0]++;
        }
        ret[1] = x[1] + (x[0] * 10);
        return ret;
    }

}

