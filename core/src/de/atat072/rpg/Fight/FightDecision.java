package de.atat072.rpg.Fight;


public class FightDecision {
    String storyName;
    String fightName;

    public FightDecision(String storyName, String fightName) {
        this.storyName = storyName;
        this.fightName = fightName;
    }

    public void loadDecision() {
        FightHandler fightHandler = new FightHandler(storyName, fightName);
    }
}
