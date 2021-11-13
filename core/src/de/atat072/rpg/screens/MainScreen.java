package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static de.atat072.rpg.RPG.INSTANCE;

public class MainScreen extends ScreenAdapter {

    private Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    private Label welcome;
    private Button newGame, loadGame, quit;
    private Stage stage;
    private Table table;
    private SpriteBatch batch;

    public MainScreen(){
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
        welcome = new Label("Wilkommen in Oradrin",skin, "optional");
        newGame = new TextButton("Neues Spiel",skin, "default");
        loadGame = new TextButton("Spielstand laden",skin,"default");
        quit = new TextButton("Beenden",skin,"default");
    }

    private void setLayout(){
        stage.addActor(table);
        table.add(welcome).fillX().expandX().pad(10);
        table.row();
        table.add(newGame).fillX().expandX().pad(10);
        table.row();
        table.add(loadGame).fillX().expandX().pad(10);
        table.row();
        table.add(quit).fillX().expandX().pad(10);
    }

    @Override
    public void render(float delta) {
        startGame();
        openSaves();
        quitGame();
        Gdx.gl.glClearColor(66,66,231,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        stage.draw();
        batch.end();
    }
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    private void startGame(){
        if(newGame.isChecked()) {
            INSTANCE.setScreen(new GameScreen());
            this.dispose();
        }
    }

    private void openSaves(){
        if(loadGame.isChecked()){
            INSTANCE.setScreen(new SaveGameSelectionScreen());
            this.dispose();
        }
    }

    private void quitGame(){
        if(quit.isChecked()){
            Gdx.app.exit();
        }
    }
}
