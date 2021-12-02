package de.atat072.rpg.Story;

import de.atat072.rpg.screens.GameScreen;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class StoryHandlerSeriasable {
    private GameScreen gameScreen;
    private String storyToStartWithOnPlay;

    public StoryHandlerSeriasable(GameScreen gameScreen, String storyToStartWithOnPlay) {
        this.gameScreen = gameScreen;
        this.storyToStartWithOnPlay = storyToStartWithOnPlay;

        //Create ArryList to check for Stories
        ArrayList<StorySeriasable> stories = new ArrayList<>();
        stories = StorySeriasable.GetAllStories();

        //Run start Story
        for (StorySeriasable s : stories) {
            if (s.name.equals(storyToStartWithOnPlay)) {
                try {
                    Method method = this.getClass().getMethod(s.name, StorySeriasable.class);
                    method.invoke(this, s);
                    return;
                } catch (Exception e) {
                    e.getCause().printStackTrace();
                }
            }
        }
        System.out.println("No Story with this Name!");
    }
}
