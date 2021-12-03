package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.atat072.rpg.RPG;

import static de.atat072.rpg.RPG.INSTANCE;

public class CreditScreen extends ScreenAdapter {
    
    private ScrollPane scrollPane;
    private Label development, gui, guiSkin, story, voiceActors;
    private Stage stage;
    private Table tableMain,table;
    private Button back;
    private SpriteBatch batch;

    public CreditScreen(){
        initialise();
        setLayout();
    }

    //creates UI elements
    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        table = new Table(RPG.SKIN);
        table.background("window");
        table.setFillParent(true);
        scrollPane = new ScrollPane(null, RPG.SKIN);
        tableMain = new Table();
        tableMain.setFillParent(true);
        back = new TextButton("Zurueck",RPG.SKIN);
        development = new Label("Development by Lennard Stubbe and Paul Henke",RPG.SKIN);
        gui = new Label("GUI design by Paul Henke", RPG.SKIN);
        guiSkin = new Label("GUI-skin by Raymond \"Raeleus\" Buckley", RPG.SKIN);
        story = new Label("story written by Paul Henke",RPG.SKIN);
        voiceActors = new Label("placeholder",RPG.SKIN);
    }

    //put the UI Elements on the Screen and set the Layout
    private void setLayout(){
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        table.add(scrollPane).expand().fill();
        scrollPane.setActor(tableMain);
        tableMain.add(development).expandX().fillX().pad(10);
        tableMain.row();
        tableMain.add(gui).expandX().fillX().pad(10);
        tableMain.row();
        tableMain.add(guiSkin).expandX().fillX().pad(10);
        tableMain.row();
        tableMain.add(story).expandX().fillX().pad(10);
        tableMain.row();
        tableMain.add(voiceActors).expandX().fillX().pad(10);
        tableMain.row();
        tableMain.add(back).expandX().fillX().pad(10);
    }

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        back();
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

    //leads back to mainScreen when the back button is pressed
    private void back(){
        if(back.isChecked()){
            INSTANCE.setScreen(new MainScreen());
            this.dispose();
        }
    }
}
