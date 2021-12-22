package de.atat072.rpg.gameObjects;

import java.util.ArrayList;

public class Player extends Char {

    private int reputation;
    private Weapon melee,range;
    private HealingPotion[] potions = new HealingPotion[4];

    public Player(String anrede, String nameArtikel1, String nameArtikel2, int str, int dex, int con, int ent, int wis, int chr,int armor,int reputation) {
        super(anrede, nameArtikel1, nameArtikel2, str, dex, con, ent, wis, chr, armor);
        this.reputation=reputation;
        setMelee(new Weapon("Kurtzschwert",6,1));
        setRange(new Weapon("Kurtzbogen",6,1));
        potions[0] = new HealingPotion(false);
        potions[1] = new HealingPotion(false);
        potions[2] = new HealingPotion(false);
        potions[3] = new HealingPotion(true);
    }

    //Use Potion and remove it by setting it null
    public void usePotion(boolean greaterHealing) {
        for (int i = 0; i < 4; i++) {
            if (greaterHealing) {
                if (potions[i] != null && potions[i].isGreaterHealing()) {
                    potions[i].use();
                    potions[i] = null;
                    return;
                }
            } else {
                if (potions[i] != null && !potions[i].isGreaterHealing()) {
                    potions[i].use();
                    potions[i] = null;
                    return;
                }
            }
        }
    }

    //stores the given healing potion in a free slot or replaces an existent if it is greater and the stored is not
    public void addPotion(HealingPotion p){
        if(!hasAllPotions()) {
            if (potions[0] == null) {potions[0] = p;}
            else if (potions[1] == null) {potions[1] = p;}
            else if (potions[2] == null) {potions[2] = p;}
            else if (potions[3] == null) {potions[3] = p;}
            else if (potions[4] == null) {potions[4] = p;}
        } else if(p.isGreaterHealing()){
            if(!potions[0].isGreaterHealing()) {potions[0] = p;}
            else if(!potions[1].isGreaterHealing()) {potions[1] = p;}
            else if(!potions[2].isGreaterHealing()) {potions[2] = p;}
            else if(!potions[3].isGreaterHealing()) {potions[3] = p;}
            else if(!potions[4].isGreaterHealing()) {potions[4] = p;}
        }
    }

    //Checks if all potions slots are used
    private boolean hasAllPotions() {
        for (HealingPotion h : potions) {
            if (h == null) {
                return false;
            }
        }
        return true;
    }

    public boolean hasNoPotions() {
        for (HealingPotion h : potions) {
            if (h != null) {
                return false;
            }
        }
        return true;
    }

    public HealingPotion[] getPotions() {
        return potions;
    }

    public void setPotions(HealingPotion[] potions) {
        this.potions = potions;
    }

    public void setMelee(Weapon newMelee){
        melee = newMelee;
    }

    public void setRange(Weapon newRage){
        range = newRage;
    }

    public int getMeleeDice(){
        return melee.getDice();
    }

    public int getMeleeDiceCount(){
        return melee.getDiceCount();
    }

    public int getRangedDice(){
        return range.getDice();
    }

    public int getRangedDiceCount(){
        return range.getDiceCount();
    }
}
