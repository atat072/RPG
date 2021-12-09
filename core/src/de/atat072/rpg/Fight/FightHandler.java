package de.atat072.rpg.Fight;

import com.badlogic.gdx.Gdx;
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

public class FightHandler {
    String introText;
    String fightName;

    ArrayList<NPC> npcList = new ArrayList<>();
    public FightHandler(String fightName) {
        this.fightName = fightName;
        npcList = readStoryFights(fightName);
        System.out.println(introText);
        refreshButtons();
    }

    public void refreshButtons() {
        //Remove the Buttons and add them again to delete the old listeners
        GameScreen.refreshButtons();

        ArrayList<NPC> npcList = readStoryFights(fightName);

        //Edit Texts on GameScreen
        GameScreen.addStoryText(introText);
        GameScreen.scrollDown();
    }

    public ArrayList<NPC> readStoryFights(String fightName) {
        ArrayList<NPC> npcList = new ArrayList<>();

        File file = new File(String.valueOf(Gdx.files.internal("Data/StoryFights.xml")));

        try {
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(file);

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();

            //Get all employees
            NodeList fightList = root.getElementsByTagName(fightName).item(0).getChildNodes();

            for (int i = 0; i < fightList.getLength(); i++) {
                Node fightNode = fightList.item(i);
                if (fightNode.getNodeType() == Node.ELEMENT_NODE) {
                    //Element fightElement = (Element) fightNode;
                    //introText = fightElement.getElementsByTagName("Intro").item(0).getTextContent();
                    if (fightNode.getNodeName().equals("Intro")) {
                        //introText = fightNode.getTextContent();
                        //System.out.println(introText);
                    }

                }
            }

//           for (int e = 0; e < storyRoots.getLength(); e++) {
//               fightName = storyRoots.item(e).getNodeName();
//
//               ArrayList<StoryDecisionValues> storyDecisionValues = new ArrayList<>();
//
//               NodeList decisionList = storyRoots.item(e).getChildNodes();
//               for (int i = 0; i < decisionList.getLength(); i++) {
//                   Node decisionNode = decisionList.item(i);
//                   if (decisionNode.getNodeType() == Node.ELEMENT_NODE) {
//                       NodeList decisionValues = decisionNode.getChildNodes();
//                       Element decisionValuesElement = (Element) decisionValues;
//
//                       storyDecisionValues.add(new StoryDecisionValues(decisionNode.getNodeName(),
//                               decisionValuesElement.getElementsByTagName("DecisionText").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision1").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision3").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision4").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision1Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision2Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision3Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision4Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("DecisionCheckTyp1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("DecisionCheckTyp3").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp4").item(0).getTextContent()
//                       ));
//                   }
//               }
//               storyMap.put(fightName, storyDecisionValues);
//           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        introText = "";

        return npcList;
    }
}
