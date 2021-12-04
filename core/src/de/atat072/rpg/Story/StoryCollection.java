package de.atat072.rpg.Story;

import java.util.ArrayList;

public class StoryCollection {
    ArrayList<Story> stories = new ArrayList<>();

    public StoryCollection() {
        this.stories = StoryHandler.createStories();
    }

    public void startStory(String storyName) {
        for (Story story : stories) {
            if (story.storyName == storyName) {
                story.start();
                return;
            }
        }
        System.out.println("No Story with that id!");
    }
}
