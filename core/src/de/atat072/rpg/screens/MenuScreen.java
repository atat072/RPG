package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static de.atat072.rpg.RPG.INSTANCE;

public class MenuScreen extends ScreenAdapter {

    private Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private Button back, save, saveAndExit;
    private GameScreen gameScreen;

    public MenuScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        initialise();
        setLayout();
    }

    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table(skin);
        table.background("window");
        table.setFillParent(true);
        back =new TextButton("Zuruek zu Spliel",skin);
        save = new TextButton("Spiel speichern", skin);
        saveAndExit = new TextButton("Speichern und Beenden",skin);
    }

    private void setLayout(){
        stage.addActor(table);
        table.add(back).expandX().fillX().pad(10);
        table.row();
        table.add(save).expandX().fillX().pad(10);
        table.row();
        table.add(saveAndExit).expandX().fillX().pad(10);
    }

    @Override
    public void render(float delta){
        backToGame();
        saveButton();
        saveAndExit();
        stage.act();
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose(){
        skin.dispose();
        stage.dispose();
        batch.dispose();
    }

    private void backToGame(){
        if(back.isChecked()){
            INSTANCE.setScreen(gameScreen);
            this.dispose();
        }
    }

    private void save(){
        Preferences prefs = Gdx.app.getPreferences(gameScreen.getName());
        //TODO implement saving https://github.com/libgdx/libgdx/wiki/Preferences
        prefs.flush();
    }

    private void saveButton(){
        if(save.isChecked()) {
            save();
            INSTANCE.setScreen(gameScreen);
            this.dispose();
        }
    }

    private void saveAndExit(){
        if(saveAndExit.isChecked()) {
            save();
            INSTANCE.setScreen(new MainScreen());
            gameScreen.dispose();
            this.dispose();
        }
    }
}
