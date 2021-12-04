package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.RPG.SKIN;

public class MainScreen extends ScreenAdapter {
    
    private Label welcome;
    private Button newGame, loadGame, quit;
    private Stage stage;
    private Table table;
    private SpriteBatch batch;

    public MainScreen(){
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
        welcome = new Label("Wilkommen in Oradrin",SKIN, "optional");
        newGame = new TextButton("Neues Spiel",SKIN, "default");
        loadGame = new TextButton("Spielstand laden",SKIN,"default");
        quit = new TextButton("Beenden",SKIN,"default");
    }

    //brings the UI Elements on the Screen with the desired layout
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

    //looped method to allow the screen to act and change appearance
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

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    //brings you to the CreateScreen
    private void startGame(){
        if(newGame.isChecked()) {
            INSTANCE.setScreen(new CreateScreen());
            this.dispose();
        }
    }

    //brings you to the SaveGameSelectionScreen
    private void openSaves(){
        if(loadGame.isChecked()){
            INSTANCE.setScreen(new SaveGameSelectionScreen());
            this.dispose();
        }
    }

    //close the Game
    private void quitGame(){
        if(quit.isChecked()){
            Gdx.app.exit();
        }
    }
}
