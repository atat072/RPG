package de.atat072.rpg.Story;

import com.badlogic.gdx.Game;
import de.atat072.rpg.RPG;
import de.atat072.rpg.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class Story {
    String storyName;
    HashMap<String, String> decisionPath = RPG.SAVE.getDecisionPath(); //Beschreibt alle Decisions die gewählt wurden um bei einem laden der Story nachlesen zu können
    HashMap<String, Decision> decisions = new HashMap<>();

    public Story(String storyName, HashMap<String, Decision> decisions) {
        this.storyName = storyName;
        this.decisions = decisions;
    }

    public void start() {
        if (decisionPath.isEmpty()) {
            decisions.get("Intro").loadDecision();
        } else {
            decisions.get("Intro").loadDecision();
            if (decisionPath.get(storyName) != null) {
                String[] savedDecisions = decisionPath.get(storyName).split(", ");
                for (String d : savedDecisions) {
                    if (decisions.keySet().contains(d)) {
                        decisions.get(d).loadDecision();
                    }
                }
            }
        }
    }
}
