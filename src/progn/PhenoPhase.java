
//Title:        Pheno-Prognosis
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn;


public class PhenoPhase 
{
   double factor;
   double exponent;
   
   public PhenoPhase(double factor, double exponent) 
   {
    this.factor = factor;
    this.exponent = exponent;
   }

   public String toString(){
     String s = factor + " " + exponent;
     return s;
   }
}
