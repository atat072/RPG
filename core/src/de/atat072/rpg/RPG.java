package de.atat072.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.atat072.rpg.screens.MainScreen;

public class RPG extends Game {
	public static RPG INSTANCE;
	public static Save SAVE;
	public static Skin SKIN;

	public RPG (){
		INSTANCE = this;
	}

	// starts the Game ín the MainScreen
	@Override
	public void create(){
		SKIN = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));

		setScreen(new MainScreen());
	}
}
