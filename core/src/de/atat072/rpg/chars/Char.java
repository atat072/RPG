package de.atat072.rpg.chars;

import java.io.Serializable;

public abstract class Char implements Serializable {

    private String name;
    private int hp,MAXHP,ac,str,con,dex,ent,wis,chr,armor;

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

    private void setAC(){
        ac = Math.max(str + armor, dex + armor);
    }
    
    private void setMAXHP(){
        MAXHP = 50+(con/2);
        hp = MAXHP;
    }

    public void takeDmg(int dmg){
        hp -= dmg;
    }

    public boolean isDead(){
        return hp<=0;
    }

    public void heal(int healing){
        if(hp+healing<=MAXHP){
            hp += healing;
        }else{
            hp = MAXHP;
        }
    }

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
