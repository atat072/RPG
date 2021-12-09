package de.atat072.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.Save;

import java.util.ArrayList;
import java.util.Objects;
import static de.atat072.rpg.RPG.SAVE;
import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.RPG.SKIN;

public class CreateScreen extends ScreenAdapter {
    
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private Label nameGame,nameChar,lSTR,lDEX,lCON,lINT,lWIS,lCHA,points;
    private TextField gameName,charName,str,dex,con,ent,wis, chr;
    private TextField.TextFieldFilter onlyNumber;
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
        table = new Table(SKIN);
        table.background("window");
        table.setFillParent(true);
        nameGame = new Label("Spielstandname:", SKIN);
        nameChar = new Label("Charaktername:", SKIN);
        gameName = new TextField("", SKIN);
        charName = new TextField("", SKIN);
        lSTR = new Label("Staerke:", SKIN);
        lDEX = new Label("Geschiklichkeit:", SKIN);
        lCON = new Label("Ausdauer:", SKIN);
        lINT = new Label("Intiligenz:", SKIN);
        lWIS = new Label("Weissheit:", SKIN);
        lCHA = new Label("Charisma:", SKIN);
        onlyNumber = new TextField.TextFieldFilter.DigitsOnlyFilter();
        str = new TextField("20",SKIN);
        str.setTextFieldFilter(onlyNumber);
        dex = new TextField("20",SKIN);
        dex.setTextFieldFilter(onlyNumber);
        con = new TextField("20",SKIN);
        con.setTextFieldFilter(onlyNumber);
        ent = new TextField("20",SKIN);
        ent.setTextFieldFilter(onlyNumber);
        wis = new TextField("20",SKIN);
        wis.setTextFieldFilter(onlyNumber);
        chr = new TextField("20",SKIN);
        chr.setTextFieldFilter(onlyNumber);
        points = new Label("Du kannst noch "+pointsLeft()+" vergeben",SKIN);
        start = new TextButton("Reise beginnen", SKIN);
        back = new TextButton("Zurueck", SKIN);

        str.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("str", str);
            }
        });
        dex.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("dex", dex);
            }
        });
        con.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("con", con);
            }
        });
        ent.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("ent", ent);
            }
        });
        wis.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("wis", wis);
            }
        });
        chr.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustInputValues("chr", chr);
            }
        });
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
        table.add(points).expandX().fillX().pad(10).colspan(2);
        table.row();
        table.add(lSTR).expandX().fillX().pad(10);
        table.add(str).expandX().fillX().pad(10);
        table.row();
        table.add(lDEX).expandX().fillX().pad(10);
        table.add(dex).expandX().fillX().pad(10);
        table.row();
        table.add(lCON).expandX().fillX().pad(10);
        table.add(con).expandX().fillX().pad(10);
        table.row();
        table.add(lINT).expandX().fillX().pad(10);
        table.add(ent).expandX().fillX().pad(10);
        table.row();
        table.add(lWIS).expandX().fillX().pad(10);
        table.add(wis).expandX().fillX().pad(10);
        table.row();
        table.add(lCHA).expandX().fillX().pad(10);
        table.add(chr).expandX().fillX().pad(10);
        table.row();
        table.add(back).expandX().fillX().pad(10);
        table.add(start).expandX().fillX().pad(10);
    }

    //looped method to allow the screen to act and change appearance
    @Override
    public void render(float delta){
        setZero();
        createGame();
        back();
        points.setText("Du kannst noch "+pointsLeft()+" vergeben");
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
        if(start.isChecked()&& validInput()){
            start.setChecked(false);
            boolean saveDontExist = true;

            FileHandle dirHandle;
            dirHandle = Gdx.files.local("saveGames");
            for(FileHandle entry: dirHandle.list()){
                if (entry.name().equals(gameName.getText() + ".ser")) {
                    saveDontExist = false;
                }
            }

            //System.out.println(saveDontExist);
            if (saveDontExist) {
                SAVE = new Save(gameName.getText()+".ser",charName.getText(),createList());
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

    private void setZero(){
        if (Objects.equals(str.getText(), "0")){
            str.setText("1");
            str.setCursorPosition(1);
        }
        if (Objects.equals(dex.getText(), "0")){
            dex.setText("1");
            dex.setCursorPosition(1);
        }
        if (Objects.equals(con.getText(), "0")){
            con.setText("1");
            con.setCursorPosition(1);
        }
        if (Objects.equals(ent.getText(), "0")){
            ent.setText("1");
            ent.setCursorPosition(1);
        }
        if (Objects.equals(wis.getText(), "0")){
            wis.setText("1");
            wis.setCursorPosition(1);
        }
        if (Objects.equals(chr.getText(), "0")){
            chr.setText("1");
            chr.setCursorPosition(1);
        }
    }

    private String pointsLeft(){
        int points = 200;
        //System.out.println(points);
        if (!str.getText().isEmpty())
            points -=Integer.parseInt(str.getText());
        //System.out.println(points);
        if (!dex.getText().isEmpty())
            points -=Integer.parseInt(dex.getText());
        //System.out.println(points);
        if (!con.getText().isEmpty())
            points -=Integer.parseInt(con.getText());
        //System.out.println(points);
        if (!ent.getText().isEmpty())
            points -=Integer.parseInt(ent.getText());
        //System.out.println(points);
        if (!wis.getText().isEmpty())
            points -=Integer.parseInt(wis.getText());
        //System.out.println(points);
        if (!chr.getText().isEmpty())
            points -=Integer.parseInt(chr.getText());
        //System.out.println(points);
        return String.valueOf(points);
    }

    private void adjustInputValues(String typ, TextField textField) {
        int pointValue = 0;

        if (!pointsLeft().equals("0"))
            pointValue = Integer.parseInt(pointsLeft());

        int value = 0;

        switch (typ) {
            case "str":
                if (!str.getText().isEmpty())
                    value = Integer.parseInt(str.getText());
                break;
            case "con":
                if (!con.getText().isEmpty())
                    value = Integer.parseInt(con.getText());
                break;
            case "dex":
                if (!dex.getText().isEmpty())
                  value = Integer.parseInt(dex.getText());
                break;
            case "ent":
                if (!ent.getText().isEmpty())
                 value = Integer.parseInt(ent.getText());
                break;
            case "wis":
                if (!wis.getText().isEmpty())
                    value = Integer.parseInt(wis.getText());
                break;
            case "chr":
                if (!chr.getText().isEmpty())
                   value = Integer.parseInt(chr.getText());
                break;

            default:
                System.out.println("Check typ do not exist!");
        }

        if (pointValue <= 0) {
            points.setText("Du kannst noch 0 vergeben");
            value = pointValue + value;
        }

        switch (typ) {
            case "str":
                str.setText(String.valueOf(value));
                break;
            case "con":
                con.setText(String.valueOf(value));
                break;
            case "dex":
                dex.setText(String.valueOf(value));
                break;
            case "ent":
                ent.setText(String.valueOf(value));
                break;
            case "wis":
                wis.setText(String.valueOf(value));
                break;
            case "chr":
                chr.setText(String.valueOf(value));
                break;

            default:
                System.out.println("Check typ do not exist!");
        }
    }

    private boolean validInput(){
        if(Objects.equals(gameName.getText(), "") || Objects.equals(charName.getText(), "")){
            return false;
        }else if(Integer.parseInt(str.getText())<1 || Integer.parseInt(dex.getText())<1){
            return false;
        }else if(Integer.parseInt(con.getText())<1 || Integer.parseInt(ent.getText())<1){
            return false;
        }else if(Integer.parseInt(wis.getText())<1 || Integer.parseInt(chr.getText())<1){
            return false;
        }else return Integer.parseInt(pointsLeft()) == 0;
    }

    private ArrayList<Integer> createList(){
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(Integer.parseInt(str.getText()));
        scores.add(Integer.parseInt(dex.getText()));
        scores.add(Integer.parseInt(con.getText()));
        scores.add(Integer.parseInt(ent.getText()));
        scores.add(Integer.parseInt(wis.getText()));
        scores.add(Integer.parseInt(chr.getText()));
        return scores;
    }
}
