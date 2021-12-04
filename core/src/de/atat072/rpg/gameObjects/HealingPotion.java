package de.atat072.rpg.gameObjects;

import static de.atat072.rpg.gameObjects.Methods.dice;

public class HealingPotion {

    private boolean greaterHealing;

    public HealingPotion(boolean greaterHealing) {
        this.greaterHealing = greaterHealing;
    }

    /*
    the use function returns the value of hp healed
    it heals 2d8+4 if its a normal potion of healing
    it heals 4d8+8 if its a potion of greater healing
     */

    public int use(){
        if(greaterHealing){
            return dice(8)+dice(8)+dice(8)+dice(8)+8;
        }else{
            return dice(8)+dice(8)+4;
        }
    }

    public boolean isGreaterHealing(){
        return greaterHealing;
    }
}
