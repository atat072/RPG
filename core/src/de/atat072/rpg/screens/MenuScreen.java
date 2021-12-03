package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.atat072.rpg.RPG;

import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.RPG.SAVE;

public class MenuScreen extends ScreenAdapter {
    
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

    //creates all UI Elements
    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table(RPG.SKIN);
        table.background("window");
        table.setFillParent(true);
        back =new TextButton("Zuruek zu Spliel",RPG.SKIN);
        save = new TextButton("Spiel speichern", RPG.SKIN);
        saveAndExit = new TextButton("Speichern und Beenden",RPG.SKIN);
    }

    //brings the UI Elements on the Screen with the desired layout
    private void setLayout(){
        stage.addActor(table);
        table.add(back).expandX().fillX().pad(10);
        table.row();
        table.add(save).expandX().fillX().pad(10);
        table.row();
        table.add(saveAndExit).expandX().fillX().pad(10);
    }

    //looped method to allow the screen to act and change appearance
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

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    @Override
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    //go back to the GameScreen
    private void backToGame(){
        if(back.isChecked()){
            INSTANCE.setScreen(gameScreen);
            this.dispose();
        }
    }

    //saves the Game
    private void save(){
        SAVE.save();
    }

    //saves the Game and returns to the GameScreen
    private void saveButton(){
        if(save.isChecked()) {
            save();
            INSTANCE.setScreen(gameScreen);
            this.dispose();
        }
    }

    //saves the Game and returns to the MainScreen
    private void saveAndExit(){
        if(saveAndExit.isChecked()) {
            save();
            SAVE.closeSave();
            INSTANCE.setScreen(new MainScreen());
            gameScreen.dispose();
            this.dispose();
        }
    }
}
