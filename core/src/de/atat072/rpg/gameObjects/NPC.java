package de.atat072.rpg.gameObjects;

public class NPC extends Char{

    private int relation;
    private Weapon weapon;
    private boolean ranged;

    public NPC(String name, int str, int dex, int con, int ent, int wis, int chr, int armor, int relation, Weapon w, boolean ranged) {
        super(name, str, dex, con, ent, wis, chr, armor);
        this.relation=relation;
        weapon = w;
        this.ranged = ranged;
    }

    public int getDice(){
        return weapon.getDice();
    }

    public int getDiceCount(){
        return weapon.getDiceCount();
    }

    public boolean isRanged() {
        return ranged;
    }
}
