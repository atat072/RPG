package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.atat072.rpg.Story.StoryCollection;

import java.io.Serializable;
import java.util.ArrayList;

import static de.atat072.rpg.RPG.INSTANCE;

public class GameScreen extends ScreenAdapter implements Serializable {

    private static Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private SpriteBatch batch;
    private Stage stage;
    private static Table table,tableText, tableOptions;
    private static AutoFocusScrollPane scrollPaneText;
    private static ArrayList<Label> storyText;
    private String name;
    private Preferences prefs;
    public static TextButton option1Btn;
    public static TextButton option2Btn;
    public static TextButton option3Btn;
    public static TextButton option4Btn;

    //Create StoryHandler to handle Story
    //private StoryHandler storyHandler;

    public GameScreen(String name){
        this.name = name;
        prefs = Gdx.app.getPreferences(name);
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
        //table.debug();
        tableText = new Table(skin);
        //tableText.debug();
        tableOptions = new Table(skin);
        tableOptions.background("dialog");
        //tableOptions.debug();
        scrollPaneText = new AutoFocusScrollPane();
        //scrollText.debug();
        scrollPaneText.setScrollbarsVisible(true);
        storyText = new ArrayList<>();
        option1Btn = new TextButton("Option1", skin);
        option2Btn = new TextButton("Option2", skin);
        option3Btn = new TextButton("Option3", skin);
        option4Btn = new TextButton("Option4", skin);

        StoryCollection storyCollection = new StoryCollection();
        storyCollection.startStory(1);
        //storyHandler = new StoryHandler(this, "Baeckerei");
    }

    private void setLayout(){
        stage.addActor(table);
        table.add(scrollPaneText).expand().fill().pad(10);
        scrollPaneText.setActor(tableText);
        for(Label l:storyText){
            tableText.add(l).expandX().fillX().pad(10);
            tableText.row();
        }
        table.row();
        table.add(tableOptions).fill();
        tableOptions.add(option1Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option2Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option3Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option4Btn).expand().fill();
        tableOptions.row();
    }

    @Override
    public void render(float delta){
        goToIngameMenu();
        stage.act(delta);
        Update();
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

    private void Update() {
        tableText.clear();
        for(Label l: storyText){
            tableText.add(l).expandX().fillX().pad(10);
            tableText.row();
        }

    }

    private void goToIngameMenu(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            INSTANCE.setScreen(new MenuScreen(this));
        }
    }

    public String getName(){
        return this.name;
    }

    public Preferences getPrefs(){
        return prefs;
    }

    public void setPrefsInt(String key, int n){
        if(key!=null&& !key.equals("")) {
            prefs.putInteger(key, n);
        }
    }

    public void setPrefsString(String key, String s){
        if(key!=null&& !key.equals("")) {
            prefs.putString(key, s);
        }
    }

    public void setPrefsBoolean(String key, boolean b){
        if(key!=null&& !key.equals("")) {
            prefs.putBoolean(key, b);
        }
    }

    public void setPrefsFloat(String key, float f){
        if(key!=null&& !key.equals("")) {
            prefs.putFloat(key, f);
        }
    }

    public void flushPrefs(){
        prefs.flush();
    }

    public static void addStoryText(String newStoryText) {
        storyText.add(new Label(newStoryText, skin));
        tableText.row();
        scrollPaneText.scrollTo(0,0,0,0);
    }

    public static void changeOptions(String newOption1, String newOption2, String newOption3, String newOption4) {
        option1Btn.setText(newOption1);
        option2Btn.setText(newOption2);
        option3Btn.setText(newOption3);
        option4Btn.setText(newOption4);
    }
}
