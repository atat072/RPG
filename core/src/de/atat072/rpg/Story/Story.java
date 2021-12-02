package de.atat072.rpg.Story;

import java.util.ArrayList;
import java.util.HashMap;

public class Story {
    ArrayList<String> decisionPath; //Beschreibt alle Decisions die gewählt wurden um bei einem laden der Story nachlesen zu können
    HashMap<String, Decision> decisions = new HashMap<>();

    public Story(ArrayList<String> decisionPath, HashMap<String, Decision> decisions) {
        this.decisionPath = decisionPath;
        this.decisions = decisions;
    }

    public void start() {
        if (decisionPath != null) {
            for (String d : decisions.keySet()) {
                if (decisionPath.contains(d)) {
                    decisions.get(d).loadDecision();
                }
            }
        } else {
            decisions.get("Intro").loadDecision();
        }
    }
}
