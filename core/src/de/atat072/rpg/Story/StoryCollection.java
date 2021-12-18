package de.atat072.rpg.Story;

import java.util.ArrayList;

public class StoryCollection {
    ArrayList<Story> stories = new ArrayList<>();

    private Story activeStory;
    private ArrayList<Decision> currentStoryDecisions = new ArrayList<>();

    public StoryCollection() {
        this.stories = StoryHandler.createStories();
        stories.remove(0);
    }

    public void startStory(String storyName) {
        for (Story story : stories) {
            if (story.storyName.equals(storyName)) {
                activeStory = story;
                for (Decision decision : activeStory.decisions.values()) {
                    currentStoryDecisions.add(decision);
                }
                story.start();
                return;
            }
        }
        System.out.println("No Story with that id!");
    }

    public void loadStoryDecision(String decisionName) {
        for (Decision decision : currentStoryDecisions) {
            if (decision.decisionName.equals(decisionName)) {
                decision.loadDecision();
                return;
            }
        }
        System.out.println("No Decision with that name!");
    }
}
