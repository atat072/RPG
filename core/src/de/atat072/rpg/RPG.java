package de.atat072.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.atat072.rpg.Story.Story;
import de.atat072.rpg.Story.StoryCollection;
import de.atat072.rpg.screens.MainScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RPG extends Game {
	public static RPG INSTANCE;
	public static Save SAVE;
	public static Skin skin;

	public RPG (){
		INSTANCE = this;
	}

	// starts the Game ín the MainScreen
	@Override
	public void create(){
		skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));

		setScreen(new MainScreen());
	}
}
