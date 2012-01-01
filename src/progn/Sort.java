
//Title:        Pheno-Prognosis
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
package progn;

public class Sort {
  public static final String[] ALLNAMES = {"Посев","Всходы","Колошение","Созревание","Выметывание","Отрастание"};
  String culture;
  String type;
  String precocity;
  PhenoPhase[] lifeCycle;
  int[] names;


  //PhenoPhase sowingToSprouting = new PhenoPhase(4.3,0.82);
  //PhenoPhase sproutingToBushing = new PhenoPhase(4.3,0.82);
  //PhenoPhase sproutingToEaring = new PhenoPhase(5.6,0.69);
  //PhenoPhase earingToRipening = new PhenoPhase(8.3,1.62);

  public Sort(String type,String precocity,String culture,PhenoPhase[] lifeCycle,int[] names) {
     this.type = type;
     this.precocity = precocity;
     this.culture = culture;
     this.lifeCycle = lifeCycle;
     this.names = names;
  }

  public String toString(){
     String info = culture + " " + type + " " + precocity + "\n";
     for(int x = 0;x< lifeCycle.length;x++){
       info+= lifeCycle[x] + "\n";
     }
     return info;
  }
}