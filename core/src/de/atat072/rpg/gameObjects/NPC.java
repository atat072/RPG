package de.atat072.rpg.gameObjects;

public class NPC extends Char{

    private int relation;
    private Weapon weapon;
    private boolean ranged;

    public NPC(String anrede, String nameArtikel1, String nameArtikel2, int str, int dex, int con, int ent, int wis, int chr, int armor, int relation, Weapon w, boolean ranged) {
        super(anrede, nameArtikel1, nameArtikel2, str, dex, con, ent, wis, chr, armor);
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
