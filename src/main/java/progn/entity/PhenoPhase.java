//Title:        Pheno-Prognosis
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn.entity;


public class PhenoPhase {
    private double factor;
    private double exponent;

    public PhenoPhase(double factor, double exponent) {
        this.factor = factor;
        this.exponent = exponent;
    }

    public String toString() {
        return factor + " " + exponent;
    }

    public double getFactor() {
        return factor;
    }

    public double getExponent() {
        return exponent;
    }
}
