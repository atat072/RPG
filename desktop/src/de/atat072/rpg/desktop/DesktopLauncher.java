package de.atat072.rpg.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.atat072.rpg.RPG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Geschichten aus Oradrin");
		config.useVsync(true);
		config.setResizable(false);
		config.setWindowedMode(960, 540);
		//config.setFullscreenMode();
		new Lwjgl3Application(new RPG(), config);
	}
}
