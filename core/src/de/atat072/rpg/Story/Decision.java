package de.atat072.rpg.Story;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.Fight.FightDecision;
import de.atat072.rpg.gameObjects.Methods;
import de.atat072.rpg.screens.GameScreen;

import static de.atat072.rpg.RPG.SAVE;

public class Decision {
    //Class which include all possible decision types
    public static class PassedValue {

        public FightDecision fightDecision;
        public String storyName;
        public Decision decision;

        public PassedValue(FightDecision fightDecision, String storyName, Decision decision) {
            this.fightDecision = fightDecision;
            this.storyName = storyName;
            this.decision = decision;
        }
    }

    //Class that executes the right command depending on which decision type is set
    public class ExecuteCommand {

        public ExecuteCommand(PassedValue passedValue) {
            if (passedValue.decision != null && passedValue.fightDecision == null && passedValue.storyName == null) {
                startDecision(passedValue.decision);
            }
            else if (passedValue.decision == null && passedValue.fightDecision != null && passedValue.storyName == null) {
                startFightDecision(passedValue.fightDecision);
            }
            else if (passedValue.decision == null && passedValue.fightDecision == null && passedValue.storyName != null) {
                startStory(passedValue.storyName);
            }
        }

        public void startDecision(Decision decision) {
            decision.loadDecision();
        }

        public void startFightDecision(FightDecision fightDecision) {
            fightDecision.loadDecision();
        }

        public void startStory(String storyName) {
            GameScreen.storyCollection.startStory(storyName);
        }
    }

    String decisionName;
    String decisionText;

    String decision1;
    TextButton decision1Btn;
    PassedValue decision1decision;

    String decision2;
    TextButton decision2Btn;
    PassedValue decision2decision;

    String decision3;
    TextButton decision3Btn;
    PassedValue decision3decision;

    String decision4;
    TextButton decision4Btn;
    PassedValue decision4decision;

    //CheckDecision Values
    Decision trueCheck;
    Decision falseCheck;

    String checkTyp;
    boolean checkResult;

    public Decision(String decisionName, String decisionText, String decision1, PassedValue decision1decision, String decision2, PassedValue decision2decision, String decision3, PassedValue decision3decision, String decision4, PassedValue decision4decision) {
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

    public void loadDecision() {
        GameScreen.refreshButtons();

        //<editor-fold desc="Set next Decision">
        //Set Option for Button 1 of this Screen
        decision1Btn = GameScreen.option1Btn;
        decision1Btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ExecuteCommand(decision1decision);
            }
        });

        //Set Option for Button 2 of this Screen
        decision2Btn = GameScreen.option2Btn;
        decision2Btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ExecuteCommand(decision2decision);
            }
        });

        //Set Option for Button 3 of this Screen
        decision3Btn = GameScreen.option3Btn;
        decision3Btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ExecuteCommand(decision3decision);
            }
        });

        //Set Option for Button 4 of this Screen
        decision4Btn = GameScreen.option4Btn;
        decision4Btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ExecuteCommand(decision4decision);
            }
        });
        //</editor-fold>

        //Edit Texts on GameScreen
        GameScreen.addStoryText(decisionText);
        GameScreen.changeOptions(decision1, decision2, decision3, decision4);
        GameScreen.scrollDown();
    }

    public static Decision checkDecision(Decision trueCheck, Decision falseCheck, String checkTyp) {
        boolean checkResultValue = true;
        //ToDO add decided decisions as String to savegame to read it later on (Start with Intro)
        if(!checkTyp.isEmpty()) {
            switch (checkTyp) {
                case "str":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getStr(), 0);
                    break;
                case "con":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getCon(), 0);
                    break;
                case "dex":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getDex(), 0);
                    break;
                case "ent":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getEnt(), 0);
                    break;
                case "wis":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getWis(), 0);
                    break;
                case "chr":
                    checkResultValue = Methods.chek(SAVE.getCharsWithIndex(0).getChr(), 0);
                    break;

                default:
                    System.out.println("Check typ do not exist!");
            }
        }
        if (checkResultValue) {
            //System.out.println("Succes");

            return trueCheck;
        } else {
            //System.out.println("Fail");

            return falseCheck;
        }
    }
}
