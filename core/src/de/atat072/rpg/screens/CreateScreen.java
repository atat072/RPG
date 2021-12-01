package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.io.IOException;
import java.util.Objects;

import static de.atat072.rpg.RPG.INSTANCE;

public class CreateScreen extends ScreenAdapter {

    private Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private Label nameGame,nameChar;
    private TextField gameName,charName;
    private Button start, back;

    public CreateScreen(){
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
        nameGame = new Label("Spielstandname:",skin);
        nameChar = new Label("Charaktername:", skin);
        gameName = new TextField("",skin);
        charName = new TextField("",skin);
        start = new TextButton("Reise beginnen",skin);
        back = new TextButton("Zurueck",skin);
    }

    private void setLayout(){
        stage.addActor(table);
        table.add(nameGame).expandX().fillX().pad(10);
        table.add(gameName).expandX().fillX().pad(10);
        table.row();
        table.add(nameChar).expandX().fillX().pad(10);
        table.add(charName).fillX().expandX().pad(10);
        table.row();
        table.add(back).expandX().fillX().pad(10);
        table.add(start).expandX().fillX().pad(10);
    }

    @Override
    public void render(float delta){
        createGame();
        back();
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

    private void createGame() {
        if(start.isChecked()&& !Objects.equals(gameName.getText(), "") && !Objects.equals(charName.getText(), "")){
            Preferences prefs = Gdx.app.getPreferences("oradrin_"+gameName.getText());
            prefs.putString("name", charName.getText());
            prefs.flush();
            INSTANCE.setScreen(new GameScreen("oradrin_"+gameName.getText()));
            this.dispose();
        }
    }

    private void back(){
        if(back.isChecked()){
            INSTANCE.setScreen(new MainScreen());
            this.dispose();
        }
    }
}
