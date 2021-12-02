package de.atat072.rpg.Story;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.screens.GameScreen;

public class DecisionCheckChoose {
    Decision trueCheck;
    Decision falseCheck;

    TextButton decisionBtn;

    boolean checkResult;

    public DecisionCheckChoose(Decision trueCheck, Decision falseCheck, TextButton decisionBtn, boolean checkResult) {
        this.trueCheck = trueCheck;
        this.falseCheck = falseCheck;
        this.checkResult = checkResult;

        this.decisionBtn = decisionBtn;
    }

    public void loadDecision(boolean checkResultValue) {
        checkResultValue = checkResult;
        if (checkResultValue) {
            decisionBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameScreen.addStoryText(trueCheck.decisionText);
                    GameScreen.changeOptions(trueCheck.decision1, trueCheck.decision2, trueCheck.decision3, trueCheck.decision4);
                    trueCheck.loadDecision();
                }
            });
        }
        else {
            decisionBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameScreen.addStoryText(falseCheck.decisionText);
                    GameScreen.changeOptions(falseCheck.decision1, falseCheck.decision2, falseCheck.decision3, falseCheck.decision4);
                    falseCheck.loadDecision();
                }
            });
        }
    }
}
