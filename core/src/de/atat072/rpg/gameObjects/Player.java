package de.atat072.rpg.gameObjects;

public class Player extends Char {

    private int reputation;
    private Weapon melee,range;
    private HealingPotion p1,p2,p3,p4,p5;

    public Player(String name, int str, int con, int dex, int ent, int wis, int chr,int armor,int reputation) {
        super(name, str, dex, con, ent, wis, chr,armor);
        this.reputation=reputation;
    }
    //uses the potion in the slot given if existent
    public void usePotion(int i){
        if(i==1&&p1!=null){p1.use();}
        else if(i==2&&p2!=null){p2.use();}
        else if(i==3&&p3!=null){p3.use();}
        else if(i==4&&p4!=null){p4.use();}
        else if(i==5&&p5!=null){p5.use();}
    }

    public boolean hasAllPotions(){
        return p1 != null && p2 != null && p3 != null && p4 != null && p5 != null;
    }

    //stores the given healing potion in a free slot or replaces an existent if it is greater and the stored is not
    public void givePotion(HealingPotion p){
        if(!hasAllPotions()) {
            if (p1 == null) {
                p1 = p;
            } else if (p2 == null) {
                p2 = p;
            } else if (p3 == null) {
                p3 = p;
            } else if (p4 == null) {
                p4 = p;
            } else if (p5 == null) {
                p5 = p;
            }
        }else if(p.isGreaterHealing()){
            if(!p1.isGreaterHealing()){p1=p;}
            else if(!p2.isGreaterHealing()){p2=p;}
            else if(!p3.isGreaterHealing()){p3=p;}
            else if(!p4.isGreaterHealing()){p4=p;}
            else if(!p5.isGreaterHealing()){p5=p;}
        }
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
