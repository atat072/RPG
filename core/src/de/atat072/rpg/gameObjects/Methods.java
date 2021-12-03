package de.atat072.rpg.gameObjects;

import java.util.ArrayList;
import java.util.Random;

public abstract class Methods {

    /*
    returns a random number between 1 and sides
    replacement for realWorld dice
     */
    public static int dice(int sides){
        Random dice = new Random();
        return 1+ dice.nextInt(sides);
    }

    //Method to calculate the Bonus you get on a check
    public static int calcBonus(ArrayList<Integer> boni){
        int temp =0;
        for (int b: boni) {
            temp+=b;
        }
        return temp;
    }

    //Method for checking if the charter succeeds on a challenge
    public static boolean chek(int score, int bonus){
        int diceResult = dice(100);

        //Debug
//        System.out.println(diceResult+bonus + " <= " + score);
//        if(diceResult+bonus <= score) {
//            System.out.println("Success");
//        } else {
//            System.out.println("Fail");
//        }

        return diceResult+bonus<=score;
    }

    //calculates if the attack hits and the damage dealt by an attack
    public static int attack(int ac, int score,int diceCount, int dice){
        int dmg = 0;
        int attack = dice(100)+(score/2);
        if(attack-ac>=0){
            while (diceCount>0){
                dmg+= dice(dice);
                diceCount-=1;
            }
            dmg+=(attack-ac);
        }
        return dmg;
    }
}
