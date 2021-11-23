package de.atat072.rpg;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.chars.Char;
import de.atat072.rpg.chars.Player;
import static de.atat072.rpg.RPG.SAVE;
import java.io.*;
import java.util.ArrayList;

public class Save implements Serializable {

    private String name;
    private ArrayList<Char> chars;
    private boolean mission1, mission2;

    public Save(String name){
        this.name = name;
        chars = new ArrayList<>();
        createChars();
        mission1 = false;
        mission2 = false;
    }

    private void createChars(){
        chars.add(new Player(name,33,34,33,33,33,34,4,0));
        //todo add all NPC when story is finished
    }

    //Todo create getter and setter for all Variables when finished

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(Gdx.files.local("saveGames/" + name + ".ser")));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void loadGame(String name){
        try {
            FileInputStream fileInputStream = new FileInputStream(String.valueOf(Gdx.files.local("saveGames/" + name + ".ser")));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SAVE = (Save) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
