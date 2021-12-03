package de.atat072.rpg.gameObjects;

public class Weapon {

    private int dice;
    private int diceCount;
    private String name;

    public Weapon(String name, int dice, int diceCount){
        this.name=name;
        this.dice=dice;
        this.diceCount=diceCount;
    }

    public int getDice() {
        return dice;
    }

    public int getDiceCount() {
        return diceCount;
    }

    public String getName() {
        return name;
    }
}
