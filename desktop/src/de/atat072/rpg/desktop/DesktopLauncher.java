package de.atat072.rpg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.atat072.rpg.RPG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Geschichten aus Oradrin";
		config.vSyncEnabled = true;
		config.resizable = false;
		config.height = 540;
		config.width = 960;
		//config.fullscreen = true;
		new LwjglApplication(new RPG(), config);
	}
}
