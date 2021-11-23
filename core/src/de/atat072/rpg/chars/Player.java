package de.atat072.rpg.chars;

public class Player extends Char{

    private int reputation;

    public Player(String name, int str, int con, int dex, int ent, int wis, int chr,int armor,int reputation) {
        super(name, str, con, dex, ent, wis, chr,armor);
        this.reputation=reputation;
    }
}
