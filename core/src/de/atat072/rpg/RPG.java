package de.atat072.rpg;

import com.badlogic.gdx.Game;
import de.atat072.rpg.Story.Story;
import de.atat072.rpg.Story.StoryCollection;
import de.atat072.rpg.screens.MainScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RPG extends Game {
	public static RPG INSTANCE;
	public RPG (){
		INSTANCE = this;
	}


	@Override
	public void create(){
		setScreen(new MainScreen());
	}
}
