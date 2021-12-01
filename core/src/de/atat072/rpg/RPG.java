package de.atat072.rpg;

import com.badlogic.gdx.Game;
import de.atat072.rpg.screens.CreditScreen;
import de.atat072.rpg.screens.MainScreen;

public class RPG extends Game {
	public static RPG INSTANCE;
	public static Save SAVE;
	public RPG (){
		INSTANCE = this;
	}


	// starts the Game ín the MainScreen
	@Override
	public void create(){
		setScreen(new MainScreen());
	}
}
