package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.ArrayList;

import static de.atat072.rpg.RPG.INSTANCE;

public class GameScreen extends ScreenAdapter {

    private Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private SpriteBatch batch;
    private Stage stage;
    private Table table,tableText, tableOptions;
    private ScrollPane scrollText;
    private ArrayList<Label> text;
    private ArrayList<Label> options;
    private String name;

    public GameScreen(String name){
        this.name = name;
        initialise();
        setLayout();
    }

    //creates all UI Elements
    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table(skin);
        table.background("window");
        table.setFillParent(true);
        //table.debug();
        tableText = new Table(skin);
        //tableText.debug();
        tableOptions = new Table(skin);
        tableOptions.background("dialog");
        //tableOptions.debug();
        scrollText = new ScrollPane(null,skin);
        //scrollText.debug();
        scrollText.setScrollbarsVisible(true);
        text = new ArrayList<>();
        options = new ArrayList<>();
    }

    //brings the UI Elements on the Screen with the desired layout
    private void setLayout(){
        stage.addActor(table);
        table.add(scrollText).expand().fill().pad(10);
        scrollText.setActor(tableText);
        for(Label l:text){
            tableText.add(l).expandX().fillX().pad(10);
            tableText.row();
        }
        table.row();
        table.add(tableOptions).expand().fill().pad(10);
        for(Label l:options){
            tableOptions.add(l).expandX().fillX().pad(10);
            tableOptions.row();
        }
    }

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        goToIngameMenu();
        stage.act(delta);
        update();
        batch.begin();
        stage.draw();
        batch.end();
    }

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    @Override
    public void dispose(){
        skin.dispose();
        stage.dispose();
        batch.dispose();
    }

    //Updates the Content of the Screen
    private void update() {
        tableText.clear();
        for(Label l:text){
            tableText.add(l).expandX().fillX().pad(10);
            tableText.row();
        }
        tableOptions.clear();
        for(Label l:options){
            tableOptions.add(l).expandX().fillX().pad(10);
            tableOptions.row();
        }
    }

    //Method to give the GameScreen the Text
    public void setText(String newText){
        text.add(new Label(newText, skin));
    }

    //Method to set the Options for the Player
    public void setOption(ArrayList<String> newOptions){
        options.clear();
        for(String s: newOptions){
            options.add(new Label(s,skin));
        }
    }

    //allows getting to the inGameMenu via Escape to save and exit the Game
    private void goToIngameMenu(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            INSTANCE.setScreen(new MenuScreen(this));
        }
    }

    public String getName(){
        return this.name;
    }

}
