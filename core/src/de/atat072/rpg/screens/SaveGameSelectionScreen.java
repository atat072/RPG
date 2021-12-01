package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.ArrayList;
import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.Save.loadGame;

public class SaveGameSelectionScreen extends ScreenAdapter {

    private Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private Stage stage;
    private Table tableOut;
    private Table tableIn;
    private SpriteBatch batch;
    private ScrollPane scrollPane;
    private Label select;
    private ArrayList<CheckBox> saveGames;
    private Button back, load;
    private ButtonGroup saveGameSelector;

    public SaveGameSelectionScreen(){
        initialise();
        setLayout();
    }

    //creates all UI Elements
    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        tableOut = new Table(skin);
        tableOut.background("window");
        tableOut.setFillParent(true);
        tableIn = new Table();
        tableIn.setFillParent(true);
        scrollPane = new ScrollPane(null,skin,"default");
        select = new Label("Welchen Spielstand willst du laden", skin, "optional");
        saveGameSelector = new ButtonGroup();
        saveGameSelector.setMaxCheckCount(1);
        saveGameSelector.setMinCheckCount(1);
        saveGames = new ArrayList<>();
        for(String g: getSaveGames()){
            CheckBox b = new CheckBox(g,skin);
            b.setName(g);
            saveGames.add(b);
        }
        for(CheckBox b: saveGames) {
            saveGameSelector.add(b);
        }
        saveGames.get(0).setChecked(true);
        back = new TextButton("Zurueck", skin, "default");
        load = new TextButton("Spielsatnd laden", skin);
    }

    //brings the UI Elements on the Screen with the desired layout
    private void setLayout(){
        stage.addActor(tableOut);
        tableOut.add(select).colspan(2).expandX().fillX().pad(10);
        tableOut.row();
        scrollPane.setActor(tableIn);
        tableOut.add(scrollPane).colspan(2).expandX().fillX().expandY().fillY().pad(10);
        for(CheckBox b: saveGames){
            tableIn.add(b).expandX().fillX().pad(10);
            tableIn.row();
        }
        tableOut.row();
        tableOut.add(back).expandX().fillX().pad(10);
        tableOut.add(load).fillX().expandX().pad(10);
    }

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        back();
        load();
        Gdx.gl.glClearColor(66,66,231,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        stage.draw();
        batch.end();
    }

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    @Override
    public void dispose(){
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    //returns all files in the saveGame directory
    private ArrayList<String> getSaveGames(){
        ArrayList<String> temp = new ArrayList<>();
        FileHandle dirHandle;
        dirHandle = Gdx.files.local("saveGames");
        for(FileHandle entry: dirHandle.list()){
            temp.add(entry.name());
        }
        return temp;
    }

    //returns you to the MainScreen
    private void back(){
        if(back.isChecked()){
            INSTANCE.setScreen(new MainScreen());
            this.dispose();
        }
    }

    //loads the SaveGame and opens the GameScreen
    private void load(){
        if(load.isChecked()){
            String temp = null;
            for(CheckBox b: saveGames){
                if(b.isChecked()){
                    temp = b.getName();
                }
            }
            loadGame(temp);
            INSTANCE.setScreen(new GameScreen(temp));
            this.dispose();
        }
    }
}
