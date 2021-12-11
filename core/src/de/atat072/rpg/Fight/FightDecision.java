package de.atat072.rpg.Fight;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.Story.Decision;
import de.atat072.rpg.Story.StoryDecisionValues;
import de.atat072.rpg.gameObjects.NPC;
import de.atat072.rpg.screens.GameScreen;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static de.atat072.rpg.RPG.SAVE;

public class FightDecision {
    String fightName;

    public FightDecision(String fightName) {
        this.fightName = fightName;
    }

    public void loadDecision() {
        FightHandler fightHandler = new FightHandler(fightName);
    }
}
