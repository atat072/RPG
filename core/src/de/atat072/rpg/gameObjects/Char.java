package de.atat072.rpg.gameObjects;

import java.io.Serializable;

public abstract class Char implements Serializable{

    private String name;
    private int hp,MAXHP,ac,str,con,dex,ent,wis,chr,armor;

    //sets all Scores for the Char
    public Char(String name, int str, int con, int dex, int ent, int wis, int chr, int armor) {
        this.name = name;
        this.str = str;
        this.con = con;
        this.dex = dex;
        this.ent = ent;
        this.wis = wis;
        this.chr = chr;
        this.armor = armor;
        setAC();
        setMAXHP();
    }

    /*
    calculates the ac
    the AC is the STR score + armor or the DEX score + armor
    you take which ever is higher
     */
    private void setAC(){
        ac = Math.max(str + armor, dex + armor);
    }

    //sets the MAXHP and hp
    private void setMAXHP(){
        MAXHP = 50+(con/2);
        hp = MAXHP;
    }

    //reduces the hp by the dmg and returns if the Char is dead
    public boolean takeDmg(int dmg){
        hp -= dmg;
        return isDead();
    }

    //checks if the hp is below 0
    private boolean isDead(){
        return hp<=0;
    }

    // you get heald by the amount given up to your MAXHP
    public void heal(int healing){
        if(hp+healing<=MAXHP){
            hp += healing;
        }else{
            hp = MAXHP;
        }
    }

    // execute this when the Char takes a long rest
    public void rest(){
        hp = MAXHP;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAc() {
        return ac;
    }

    public int getStr() {
        return str;
    }

    public int getCon() {
        return con;
    }

    public int getDex() {
        return dex;
    }

    public int getEnt() {
        return ent;
    }

    public int getWis() {
        return wis;
    }

    public int getChr() {
        return chr;
    }
}
