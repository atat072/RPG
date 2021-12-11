package de.atat072.rpg.gameObjects;

public class NPC extends Char{

    private int relation;
    private Weapon weapon;

    public NPC(String name, int str, int con, int dex, int ent, int wis, int chr, int armor, int relation, Weapon w) {
        super(name, str, dex, con, ent, wis, chr, armor);
        this.relation=relation;
        weapon = w;
    }

    public int getDice(){
        return weapon.getDice();
    }

    public int getDiceCount(){
        return weapon.getDiceCount();
    }
}
