package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.atat072.rpg.Story.StoryCollection;

import java.io.Serializable;
import java.util.ArrayList;

import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.RPG.SKIN;

public class GameScreen extends ScreenAdapter implements Serializable {
    
    private SpriteBatch batch;
    private Stage stage;
    private static Table table,tableText, tableOptions;
    private static AutoFocusScrollPane scrollPaneText;
    private static ArrayList<Label> storyText;
    private String name;
    public static TextButton option1Btn;
    public static TextButton option2Btn;
    public static TextButton option3Btn;
    public static TextButton option4Btn;

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
        table = new Table(SKIN);
        table.background("window");
        table.setFillParent(true);
        //table.debug();
        tableText = new Table(SKIN);
        //tableText.debug();
        tableOptions = new Table(SKIN);
        tableOptions.background("dialog");
        //tableOptions.debug();
        scrollPaneText = new AutoFocusScrollPane();
        //scrollText.debug();
        scrollPaneText.setScrollbarsVisible(true);
        storyText = new ArrayList<>();

        StoryCollection storyCollection = new StoryCollection();
        storyCollection.startStory(1);
        //storyHandler = new StoryHandler(this, "Baeckerei");
    }

    //brings the UI Elements on the Screen with the desired layout
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
    }

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        goToIngameMenu();
        stage.act(delta);
        Update();
        batch.begin();
        stage.draw();
        batch.end();
    }

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    //TODO Causing Errors on reopen the screen
    @Override
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    //Updates the Content of the Screen
    private void Update() {
        tableText.clear();
        for(Label l: storyText){
            tableText.add(l).expandX().fillX().pad(10);
            tableText.row();
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

    //Add text to the Story table
    public static void addStoryText(String newStoryText) {
        storyText.add(new Label(newStoryText, SKIN));
        tableText.row();
    }

    public static void scrollDown() {
        scrollPaneText.layout();
        scrollPaneText.setScrollY(scrollPaneText.getMaxY());
    }

    //Chnage the Text of the Option buttons
    public static void changeOptions(String newOption1, String newOption2, String newOption3, String newOption4) {
        option1Btn.setText(newOption1);
        option2Btn.setText(newOption2);
        option3Btn.setText(newOption3);
        option4Btn.setText(newOption4);
    }

    //Refreshes the Buttons after taking a decision
    public static void refreshButtons() {
        if (option1Btn != null)
            option1Btn.remove();

        if (option2Btn != null)
            option2Btn.remove();

        if (option3Btn != null)
            option3Btn.remove();

        if (option4Btn != null)
            option4Btn.remove();

        option1Btn = new TextButton("Option1", SKIN);
        option2Btn = new TextButton("Option2", SKIN);
        option3Btn = new TextButton("Option3", SKIN);
        option4Btn = new TextButton("Option4", SKIN);

        tableOptions.add(option1Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option2Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option3Btn).expand().fill();
        tableOptions.row();
        tableOptions.add(option4Btn).expand().fill();
        tableOptions.row();
    }
}
