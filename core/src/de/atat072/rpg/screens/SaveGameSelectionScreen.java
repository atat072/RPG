package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;

import static de.atat072.rpg.RPG.INSTANCE;

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
            saveGames.add(new CheckBox( g,skin));
        }
        for(CheckBox b: saveGames) {
            saveGameSelector.add(b);
        }
        saveGames.get(0).setChecked(true);
        back = new TextButton("Zurueck", skin, "default");
        load = new TextButton("Spielsatnd laden", skin);
    }

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

    @Override
    public void dispose(){
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    private ArrayList<String> getSaveGames(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("not yet implemented");
        return temp;
    }

    private void back(){
        if(back.isChecked()){
            INSTANCE.setScreen(new MainScreen());
            this.dispose();
        }
    }
    private void load(){
        if(load.isChecked()){
            //INSTANCE.setScreen(new GameScreen());
            this.dispose();
        }
    }
}
