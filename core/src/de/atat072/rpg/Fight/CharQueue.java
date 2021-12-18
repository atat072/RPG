package de.atat072.rpg.Fight;

import de.atat072.rpg.gameObjects.Char;

import java.util.ArrayList;

public class CharQueue {
    private ArrayList<Char> chars;

    public CharQueue(){
        chars = new ArrayList<>();
    }

    public void add(Char c){
        chars.add(c);
    }

    public Char retrieve(){
        Char c= chars.get(0);
        chars.remove(0);
        return c;
    }

    public int size(){
        return chars.size();
    }

    public void remove(Char c){
        chars.remove(c);
    }
}
