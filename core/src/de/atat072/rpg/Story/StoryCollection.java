package de.atat072.rpg.Story;

import java.util.ArrayList;

public class StoryCollection {
    ArrayList<Story> stories = new ArrayList<>();

    public static Story activeStory;

    public StoryCollection() {
        this.stories = StoryHandler.createStories();
        stories.remove(0);
    }

    public void startStory(String storyName) {
        for (Story story : stories) {
            if (story.storyName.equals(storyName)) {
                activeStory = story;
                story.start();
                return;
            }
        }
        System.out.println("No Story with that id!");
    }
}
