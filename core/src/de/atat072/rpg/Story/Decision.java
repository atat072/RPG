package de.atat072.rpg.Story;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.screens.GameScreen;

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

    TextButton decisionBtn;

    String checkTyp;
    boolean checkResult;

    public Decision(String decisionName, String decisionText, String decision1, Decision decision1decision, String decision2, Decision decision2decision, String decision3, Decision decision3decision, String decision4, Decision decision4decision) {
        this.decisionName = decisionName;
        this.decisionText = decisionText;

        this.decision1 = decision1;
        this.decision1Btn = GameScreen.option1Btn;
        this.decision1decision = decision1decision;

        this.decision2 = decision2;
        this.decision2Btn = GameScreen.option2Btn;
        this.decision2decision = decision2decision;

        this.decision3 = decision3;
        this.decision3Btn = GameScreen.option3Btn;
        this.decision3decision = decision3decision;

        this.decision4 = decision4;
        this.decision4Btn = GameScreen.option4Btn;
        this.decision4decision = decision4decision;

        this.checkResult = false;
    }

    //CheckDecision Methods
    public Decision(Decision trueCheck, Decision falseCheck, TextButton decisionBtn, String checkTyp) {
        this.trueCheck = trueCheck;
        this.falseCheck = falseCheck;
        this.checkResult = true;
        this.checkTyp = checkTyp;

        this.decisionBtn = decisionBtn;
    }

    public void loadDecision() {
        if (checkResult) {
            System.out.println(trueCheck.decisionText);
            System.out.println(falseCheck.decisionText);
            boolean checkResultValue = true;
            //ToDO add decided decisions as String to savegame to read it later on (Start with Intro)
            //ToDo implement check
            switch (checkTyp) {
                case "str":
                    //checkResultValue = Methods.chek();
                    break;
                case "con":
                    //checkResultValue = Methods.chek();
                    break;
                case "dex":
                    //checkResultValue = Methods.chek();
                    break;
                case "ent":
                    //checkResultValue = Methods.chek();
                    break;
                case "wis":
                    //checkResultValue = Methods.chek();
                    break;
                case "chr":
                    //checkResultValue = Methods.chek();
                    break;

                default:
                    System.out.println("Check typ do not exist!");
            }
            if (checkResultValue) {
                EventListener defaultListener = decisionBtn.getListeners().get(0);
                if (decisionBtn.getListeners().size > 0) {
                    for (EventListener listener : decisionBtn.getListeners()) {
                        decisionBtn.removeListener(listener);
                    }
                    decisionBtn.addListener(defaultListener);
                }
                decisionBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GameScreen.addStoryText(trueCheck.decisionText);
                        GameScreen.changeOptions(trueCheck.decision1, trueCheck.decision2, trueCheck.decision3, trueCheck.decision4);
                        checkResult = false;
                        trueCheck.loadDecision();
                    }
                });
            } else {
                EventListener defaultListener = decisionBtn.getListeners().get(0);
                if (decisionBtn.getListeners().size > 0) {
                    for (EventListener listener : decisionBtn.getListeners()) {
                        decisionBtn.removeListener(listener);
                    }
                    decisionBtn.addListener(defaultListener);
                }
                decisionBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GameScreen.addStoryText(falseCheck.decisionText);
                        GameScreen.changeOptions(falseCheck.decision1, falseCheck.decision2, falseCheck.decision3, falseCheck.decision4);
                        checkResult = false;
                        falseCheck.loadDecision();
                    }
                });
            }
        } else {
//            System.out.println(decision1decision.decisionText);
//            System.out.println(decision2decision.decisionText);
//            System.out.println(decision3decision.decisionText);
//            System.out.println(decision4decision.decisionText);

            //Handel Option Button 1
            EventListener defaultListenerBtn1 = decision1Btn.getListeners().get(0);
            if (decision1Btn.getListeners().size > 0) {
                for (EventListener listener : decision1Btn.getListeners()) {
                    decision1Btn.removeListener(listener);
                }
                decision1Btn.addListener(defaultListenerBtn1);
            }
            if (decision1decision != null) {
                decision1Btn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        decision1decision.loadDecision();
                    }
                });
            }

            //Handel Option Button 2
            EventListener defaultListenerBtn2 = decision2Btn.getListeners().get(0);
            System.out.println(decision2Btn.getListeners().size);
            if (decision2Btn.getListeners().size > 0) {
                for (EventListener listener : decision2Btn.getListeners()) {
                    decision2Btn.removeListener(listener);
                }
                decision2Btn.addListener(defaultListenerBtn2);
            }
            if (decision2decision != null) {
                decision2Btn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        decision2decision.loadDecision();
                    }
                });
            }
            System.out.println(decision2Btn.getListeners().size);
            //Handel Option Button 3
            EventListener defaultListenerBtn3 = decision3Btn.getListeners().get(0);
            if (decision3Btn.getListeners().size > 0) {
                for (EventListener listener : decision3Btn.getListeners()) {
                    decision3Btn.removeListener(listener);
                }
                decision3Btn.addListener(defaultListenerBtn3);
            }
            if (decision3decision != null) {
                decision3Btn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        decision3decision.loadDecision();
                    }
                });
            }

            //Handel Option Button 4
            EventListener defaultListenerBtn4 = decision4Btn.getListeners().get(0);
            if (decision4Btn.getListeners().size > 0) {
                for (EventListener listener : decision4Btn.getListeners()) {
                    decision4Btn.removeListener(listener);
                }
                decision4Btn.addListener(defaultListenerBtn4);
            }
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
        }
    }
}
