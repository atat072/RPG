package de.atat072.rpg.chars;

import java.util.ArrayList;
import java.util.Random;

public abstract class Methods {

    private static int dice(int sides){
        Random dice = new Random();
        return 1+ dice.nextInt(sides);
    }

    public static int calcBonus(ArrayList<Integer> boni){
        int temp =0;
        for (int b: boni) {
            temp+=b;
        }
        return temp;
    }

    public static boolean chek(int score, int bonus){
        return dice(100)+bonus<score;
    }

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
