package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CreditScreen extends ScreenAdapter {

    Skin skin = new Skin(Gdx.files.internal("gdx-skins-master/commodore64/skin/uiskin.json"));
    ScrollPane scrollPane;
    Label development, gui, guiSkin, story, voiceActors;
    Stage stage;
    Table tableMain,table;
    SpriteBatch batch;

    public CreditScreen(){
        initialise();
        setLayout();
    }

    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        table = new Table(skin);
        table.setFillParent(true);
        scrollPane = new ScrollPane(null, skin);
        scrollPane.setFillParent(true);
        tableMain = new Table();
        tableMain.setFillParent(true);
        development = new Label("Development by Lennard Stubbe and Paul Henke",skin);
        gui = new Label("GUI design by Paul Henke", skin);
        guiSkin = new Label("GUI-skin by Raymond \"Raeleus\" Buckley", skin);
        story = new Label("story written by Paul Henke",skin);
        voiceActors = new Label("placeholder",skin);
    }

    private void setLayout(){
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        table.add(scrollPane);
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
    }

    @Override
    public void render(float delta){
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
}
