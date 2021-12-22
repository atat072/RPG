package de.atat072.rpg.gameObjects;

import de.atat072.rpg.RPG;
import de.atat072.rpg.Save;

import java.io.Serializable;

import static de.atat072.rpg.gameObjects.Methods.dice;
import static de.atat072.rpg.screens.GameScreen.addStoryText;

public class HealingPotion implements Serializable {

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
            int healValue = dice(8)+dice(8)+dice(8)+dice(8)+8;
            int newHP = RPG.SAVE.getCharsWithIndex(0).getHp() + healValue;
            addStoryText("Du nimmst einen grossen Heiltrank zu dir und heilst \ndich von " + RPG.SAVE.getCharsWithIndex(0).getHp() + " Leben auf " + ((newHP > RPG.SAVE.getCharsWithIndex(0).getMAXHP()) ? RPG.SAVE.getCharsWithIndex(0).getMAXHP() : newHP) + " Leben hoch");
            RPG.SAVE.getCharsWithIndex(0).heal(healValue);
            return healValue;
        }else{
            int healValue = dice(8)+dice(8)+4;
            int newHP = RPG.SAVE.getCharsWithIndex(0).getHp() + healValue;
            addStoryText("Du nimmst einen kleinen Heiltrank zu dir und heilst \ndich von " + RPG.SAVE.getCharsWithIndex(0).getHp() + " Leben auf " + ((newHP > RPG.SAVE.getCharsWithIndex(0).getMAXHP()) ? RPG.SAVE.getCharsWithIndex(0).getMAXHP() : newHP) + " Leben hoch");
            RPG.SAVE.getCharsWithIndex(0).heal(healValue);
            return healValue;
        }
    }

    public boolean isGreaterHealing(){
        return greaterHealing;
    }
}
