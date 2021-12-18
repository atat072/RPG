package de.atat072.rpg.Fight;


public class FightDecision {
    String fightName;

    public FightDecision(String fightName) {
        this.fightName = fightName;
    }

    public void loadDecision() {
        FightHandler fightHandler = new FightHandler(fightName);
    }
}
