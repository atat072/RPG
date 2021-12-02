package de.atat072.rpg.Story;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.chars.Methods;
import de.atat072.rpg.screens.GameScreen;

import static de.atat072.rpg.RPG.SAVE;

public class Decision {
    String decisionName;
    String decisionText;

    String decision1;
    TextButton decision1Btn;
    Decision decision1decision;

    String decision2;
    TextButton decision2Btn;
    Decision decision2decision;

    String decision3;
    TextButton decision3Btn;
    Decision decision3decision;

    String decision4;
    TextButton decision4Btn;
    Decision decision4decision;

    //CheckDecision Values
    Decision trueCheck;
    Decision falseCheck;

    int decisionBtnNumber;

    String checkTyp;
    boolean checkResult;

    public Decision(String decisionName, String decisionText, String decision1, Decision decision1decision, String decision2, Decision decision2decision, String decision3, Decision decision3decision, String decision4, Decision decision4decision) {
        this.decisionName = decisionName;
        this.decisionText = decisionText;

        this.decision1 = decision1;
        this.decision1decision = decision1decision;

        this.decision2 = decision2;
        this.decision2decision = decision2decision;

        this.decision3 = decision3;
        this.decision3decision = decision3decision;

        this.decision4 = decision4;
        this.decision4decision = decision4decision;

        this.checkResult = false;
    }

    //CheckDecision Methods
    public Decision() {
        this.trueCheck = trueCheck;
        this.falseCheck = falseCheck;
        this.decisionBtnNumber = decisionBtnNumber;
        this.checkTyp = checkTyp;
        this.checkResult = true;
    }

    public void loadDecision() {
        GameScreen.refreshButtons();

        //Set Option for Button 1 of this Screen
        decision1Btn = GameScreen.option1Btn;

        if (decision1decision != null) {
            decision1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    decision1decision.loadDecision();
                }
            });
        }

        //Set Option for Button 2 of this Screen
        decision2Btn = GameScreen.option2Btn;

        if (decision2decision != null) {
            decision2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    decision2decision.loadDecision();
                }
            });
        }

        //Set Option for Button 3 of this Screen
        decision3Btn = GameScreen.option3Btn;

        if (decision3decision != null) {
            decision3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    decision3decision.loadDecision();
                }
            });
        }

        //Set Option for Button 4 of this Screen
        decision4Btn = GameScreen.option4Btn;

        if (decision4decision != null) {
            decision4Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    decision4decision.loadDecision();
                }
            });
        }

        //Edit Texts on GameScreen
        GameScreen.addStoryText(decisionText);
        GameScreen.changeOptions(decision1, decision2, decision3, decision4);
        GameScreen.scrollDown();
    }

    public static Decision checkDecision(Decision trueCheck, Decision falseCheck, String checkTyp) {
        boolean checkResultValue = true;
        //ToDO add decided decisions as String to savegame to read it later on (Start with Intro)
        switch (checkTyp) {
            case "str":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getStr(),0);
                break;
            case "con":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getCon(),0);
                break;
            case "dex":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getDex(),0);
                break;
            case "ent":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getEnt(),0);
                break;
            case "wis":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getWis(),0);
                break;
            case "chr":
                checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getChr(),0);
                break;

            default:
                System.out.println("Check typ do not exist!");
        }
        if (checkResultValue) {
            System.out.println("Succes");

            return trueCheck;
        } else {
            System.out.println("Fail");

            return falseCheck;
        }
    }
}
