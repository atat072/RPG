package de.atat072.rpg.Fight;


public class FightDecision {
    int fightNumber;
    String fightName;

    public FightDecision(String fightName, int fightNumber) {
        this.fightNumber = fightNumber;
        this.fightName = fightName;
    }

    public void loadDecision() {
        FightHandler fightHandler = new FightHandler(fightName);
    }

    public int getFightNumber() {
        return fightNumber;
    }
}
