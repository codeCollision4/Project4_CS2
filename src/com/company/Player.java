package com.company;

import java.util.Comparator;

public class Player implements Comparable<Player>{

    //Variables
    String name;
    double hits;
    double walks;
    double strikeOuts;
    double hitByPitch;
    double sacFly;
    double errors;
    double outs;

    //Default Constructor
    public Player() {
        hits = 0.000;
        outs = 0.000;
        errors = 0.000;
        strikeOuts = 0.000;
        walks = 0.000;
        hitByPitch = 0.000;
        sacFly = 0.000;
        name = "";
    };
    //Constructor that uses name given in a string
    public Player(String n){
        hits = 0.000;
        outs = 0.000;
        errors = 0.000;
        strikeOuts = 0.000;
        walks = 0.000;
        hitByPitch = 0.000;
        sacFly = 0.000;
        name = n;
    }

    //Setters
    public void setName(String person) {name = person;}
    public void setHit(double hit) {hits = hit;}
    public void setWalk(double walk) {walks = walk;}
    public void setStrikeOut(double strikeOut) {strikeOuts = strikeOut;}
    public void setPitchHits(double pitchHit) {hitByPitch = pitchHit;}
    public void setSacFly(double sacrifice) {sacFly = sacrifice;}
    public void setErrors(double error) {errors = error;}
    public void setOut(double out) {outs = out;}

    //Mutators
    public void addHit() {hits++;}
    public void addWalk() {walks++;}
    public void addStrikeOut() {strikeOuts++;}
    public void addPitchHits() {hitByPitch++;}
    public void addSacFly() {sacFly++;}
    public void addError() {errors++;}
    public void addOut() {outs++;}

    //Getters
    public String getName() {return name;}
    public double getHit() {return hits;}
    public double getWalk() {return walks;}
    public double getStrikeOut() {return strikeOuts;}
    public double getPitchHits() {return hitByPitch;}
    public double getSacFly() {return sacFly;}
    public double getErrors() {return errors;}
    public double getOut() {return outs;}

    //CompareTo
    @Override
    public int compareTo(Player player){
        return this.name.compareTo(player.name);
    }

    //toString
    @Override
    public String toString(){

        //Building String based on order of stats to print
        return (int)atBats() + "\t" + (int)hits + "\t" + (int)walks + "\t" + (int)strikeOuts + "\t" + (int)hitByPitch + "\t" + (int)sacFly + "\t" + String.format("%.3f",batAvgCalc()) + "\t" + String.format("%.3f",onBasePer());

    }

    /******
     Start of Functions
     *******/
    public double batAvgCalc() {


        //Calculating Batting Average if denominator is not 0
        if (atBats() != 0) {
              return hits / atBats();
        }
        else{
            return 0.000;   //Returning 0 if denominator doesnt exist
        }


    }//End of batAvgCalc

    public double onBasePer() {

        //Calculating On Base Percentage if denominator is not 0
        if (plateApp() != 0) {
           return (hits + walks + hitByPitch) / plateApp();
        }
        else{
            return 0.000;   //Returning 0 if denominator doesnt exist
        }

    }//End of onBasePer

    public double atBats(){
        return hits + outs + strikeOuts + errors;    //Adds up at bat stats then returns to be printed
    }//End of atBats

   public double plateApp(){
        return hits + outs + strikeOuts + walks + hitByPitch + sacFly + errors; //Adds up all plate apps
   }//End of plate apperances

}//end of Player class

class PlayerHitsComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.hits - (int)p2.hits;
    }
}
class PlayerWalksComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.walks - (int)p2.walks;
    }
}
class PlayerStikeoutsComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.strikeOuts - (int)p2.strikeOuts;
    }
}
class PlayerHitByPitchComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.hitByPitch - (int)p2.hitByPitch;
    }
}
class PlayerSacrificeComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.sacFly - (int)p2.sacFly;
    }
}
class PlayerErrorsComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.errors - (int)p2.errors;
    }
}
class PlayerOutsComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return (int)p1.outs - (int)p2.outs;
    }
}
class PlayerAtBatComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return Double.compare(p2.atBats(), p1.atBats());
    }
}
class PlayerBatAvgComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return Double.compare(p1.batAvgCalc(), p2.batAvgCalc());
    }
}
class PlayerOnBasePerComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return Double.compare(p1.onBasePer(),p2.onBasePer());
    }
}