package de.atat072.rpg.Story;

import java.util.HashMap;

public class StoryCollection {
    HashMap<Integer, Story> stories = new HashMap<>();

    public StoryCollection() {
        StoryHandler storyHandler = new StoryHandler();
        this.stories = storyHandler.stories;
    }

    public void startStory(int storyId) {
        for (int i : stories.keySet()) {
            if (i == storyId) {
                stories.get(i).start();
                return;
            }
        }

        System.out.println("No Story with that id!");
    }
}
