package de.atat072.rpg.Story;

import java.io.*;
import java.util.ArrayList;

public class StorySeriasable implements Serializable {
    public static String storiesPath;

    public String name = "";

    public String story = "";

    public StorySeriasable(String name, String story) {
        this.name = name;
        this.story = story;
    }

    //Write a new Story
    public static void CreateStorysFile (String storiesPath) {
        try {
            //Create Empty ArrayList
            ArrayList<StorySeriasable> stories = new ArrayList<StorySeriasable>();

            //Create new .storyies File
            FileOutputStream fileOut = new FileOutputStream(storiesPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stories);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Write a new Story
    public static void WriteStory (StorySeriasable newStory) {
        try {
            //Load current Storys and add the new one
            ArrayList<StorySeriasable> stories = new ArrayList<StorySeriasable>();
            if (GetAllStories() != null)
                stories = GetAllStories();
            stories.add(newStory);

            //Add the new Story to the File
            FileOutputStream fileOut = new FileOutputStream(storiesPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stories);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Delete a Story
    public static void DeleteStory (String storyToRemove) {
        try {
            //Load the current Storys and remove the desired Story
            ArrayList<StorySeriasable> stories = new ArrayList<StorySeriasable>();
            stories = GetAllStories();

            StorySeriasable toRemove = null;

            for (StorySeriasable s : stories) {
                if (s.name.equals(storyToRemove)) {
                    toRemove = s;
                    break;
                }
            }

            stories.remove(toRemove);

            //Update the File
            FileOutputStream fileOut = new FileOutputStream(storiesPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stories);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Get a Story
    public static StorySeriasable GetStory (String storyName) {
        try {
            //Load the Story
            FileInputStream fileIn = new FileInputStream(storiesPath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            //Load Stories in to ArrayList of Stories
            ArrayList<StorySeriasable> stories = new ArrayList<StorySeriasable>();
            stories = (ArrayList<StorySeriasable>) in.readObject();

            System.out.println(stories.size());

            //Look for desired story and return it
            for (StorySeriasable s : stories) {
                if (s.name.equals(storyName)) {
                    return s;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //Get all stories
    public static ArrayList<StorySeriasable> GetAllStories () {
        ArrayList<StorySeriasable> stories = new ArrayList<StorySeriasable>();
        try {
            //Load the Story
            FileInputStream fileIn = new FileInputStream(storiesPath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            //Load Stories in to ArrayList of Stories
            stories = (ArrayList<StorySeriasable>) in.readObject();

        } catch (Exception e) {
            System.out.println(e);
        }
        return stories;
    }

    //Get all stories
    public static void SetStories (ArrayList<StorySeriasable> newStories) {
        try {
            //Set the new Story to the File
            FileOutputStream fileOut = new FileOutputStream(storiesPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newStories);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
