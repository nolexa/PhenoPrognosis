//Title:        Pheno-Prognosis
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn.entity;

public class Sort {
    public static final String[] ALLNAMES = {"Посев", "Всходы", "Колошение", "Созревание", "Выметывание", "Отрастание"};
    private String culture;
    private String type;
    private String precocity;
    private PhenoPhase[] lifeCycle;
    private int[] names;


    //PhenoPhase sowingToSprouting = new PhenoPhase(4.3,0.82);
    //PhenoPhase sproutingToBushing = new PhenoPhase(4.3,0.82);
    //PhenoPhase sproutingToEaring = new PhenoPhase(5.6,0.69);
    //PhenoPhase earingToRipening = new PhenoPhase(8.3,1.62);

    public Sort(String type, String precocity, String culture, PhenoPhase[] lifeCycle, int[] names) {
        this.type = type;
        this.precocity = precocity;
        this.culture = culture;
        this.lifeCycle = lifeCycle;
        this.names = names;
    }

    public String toString() {
        StringBuilder info = new StringBuilder(String.format("%s %s %s\n", culture, type, precocity)) ;
        for (PhenoPhase aLifeCycle : lifeCycle) {
            info.append(aLifeCycle);
        }
        return info.toString();
    }

    public String getCulture() {
        return culture;
    }

    public String getType() {
        return type;
    }

    public String getPrecocity() {
        return precocity;
    }

    public PhenoPhase[] getLifeCycle() {
        return lifeCycle;
    }

    public int[] getNames() {
        return names;
    }

    public String getDescription() {
        return getCulture() + " " + getType() + " " + getPrecocity();
    }
}