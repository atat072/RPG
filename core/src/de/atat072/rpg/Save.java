package de.atat072.rpg;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.gameObjects.Char;
import de.atat072.rpg.gameObjects.Player;
import static de.atat072.rpg.RPG.SAVE;
import java.io.*;
import java.util.ArrayList;

public class Save implements Serializable {

    private String name;
    private ArrayList<Char> chars;
    private boolean mission1, mission2;

    public Save(String gameName, String charName, ArrayList<Integer> scores){
        this.name = gameName;
        chars = new ArrayList<>();
        createChars(charName, scores);
        mission1 = false;
        mission2 = false;
    }

    /*
    Creates the Player and NPC when a new Game is created
     */
    private void createChars(String charName, ArrayList<Integer> scores){
        chars.add(new Player("dich", charName, charName, scores.get(0), scores.get(1),scores.get(2) ,scores.get(3), scores.get(4), scores.get(5),4,0));
        //todo add all NPC when story is finished
    }

    //Todo create getter and setter for all Variables when story is finished

    /*
    This method serializes the Save class and saves it in the saveGame directory
     */
    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(Gdx.files.local("saveGames/" + name)));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    // Used to load the serialized Save class
    public static void loadGame(String name){
        try {
            FileInputStream fileInputStream = new FileInputStream(String.valueOf(Gdx.files.local("saveGames/" + name)));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SAVE = (Save) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Get Chars with name
    public Char getCharsWithName(String name) {
        for (Char c : chars) {
            if (c.getNameArtikel1().equals(name)) {
                return c;
            }
        }

        return null;
    }

    //Get Chars with index
    public Char getCharsWithIndex(int index) {
        return chars.get(index);
    }

    //Close save
    public void closeSave() {
        SAVE = null;
    }
}
