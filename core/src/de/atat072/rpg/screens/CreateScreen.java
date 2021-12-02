package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.atat072.rpg.RPG;
import de.atat072.rpg.Save;

import java.util.Objects;
import static de.atat072.rpg.RPG.SAVE;
import static de.atat072.rpg.RPG.INSTANCE;

public class CreateScreen extends ScreenAdapter {
    
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

    //creates all UI Elements
    private void initialise(){
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table(RPG.skin);
        table.background("window");
        table.setFillParent(true);
        nameGame = new Label("Spielstandname:",RPG.skin);
        nameChar = new Label("Charaktername:", RPG.skin);
        gameName = new TextField("",RPG.skin);
        charName = new TextField("",RPG.skin);
        start = new TextButton("Reise beginnen",RPG.skin);
        back = new TextButton("Zurueck",RPG.skin);
    }

    //brings the UI Elements on the Screen with the desired layout
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

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        createGame();
        back();
        stage.act();
        batch.begin();
        stage.draw();
        batch.end();
    }

    //disposes the UI Elements when the screen gets closed to reduce ram usage
    @Override
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    //create the save Object and starts the Game
    private void createGame(){
        if(start.isChecked()&& !Objects.equals(gameName.getText(), "") && !Objects.equals(charName.getText(), "")){
            boolean saveDontExist = true;

            FileHandle dirHandle;
            dirHandle = Gdx.files.local("saveGames");
            for(FileHandle entry: dirHandle.list()){
                if (entry.name().equals(gameName.getText() + ".ser")) {
                    saveDontExist = false;
                }
            }

            System.out.println(saveDontExist);
            if (saveDontExist) {
                SAVE = new Save(gameName.getText());
                INSTANCE.setScreen(new GameScreen("oradrin_" + gameName.getText()));
                this.dispose();
            }
        }
    }

    //leads back to mainScreen when the back button is pressed
    private void back(){
        if(back.isChecked()){
            INSTANCE.setScreen(new MainScreen());
            this.dispose();
        }
    }
}
