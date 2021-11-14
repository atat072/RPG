package de.atat072.rpg.chars;

public class NPC extends Char{

    private int relation;

    public NPC(String name, int hp, int MAXHP, int str, int con, int dex, int ent, int wis, int chr, int armor, int relation) {
        super(name, hp, MAXHP, str, con, dex, ent, wis, chr, armor);
        this.relation=relation;
    }
}
