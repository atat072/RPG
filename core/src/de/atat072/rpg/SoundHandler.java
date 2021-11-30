package de.atat072.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public abstract class SoundHandler {

    public static void playSound(String file){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/"+file));
        sound.play();
    }

    public static void playMusic(String file){
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Music/"+file));
        music.play();
    }

    public static void playDialog(String file){
        Sound dialog = Gdx.audio.newSound(Gdx.files.internal("Audio/Dialog/"+file));
        dialog.play();
    }
}
